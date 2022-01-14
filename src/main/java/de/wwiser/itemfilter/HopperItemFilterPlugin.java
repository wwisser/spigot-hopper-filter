package de.wwiser.itemfilter;

import de.wwiser.itemfilter.infra.CachedBlockingHopperFilterService;
import de.wwiser.itemfilter.infra.HopperFilterEntity;
import de.wwiser.itemfilter.infra.repository.JpaHopperFilterRepository;
import de.wwiser.itemfilter.presentation.HopperFilterViewStateRegistry;
import de.wwiser.itemfilter.presentation.evaluator.HopperFilterEvaluatorImpl;
import de.wwiser.itemfilter.presentation.factory.HopperFilterInventoryFactory;
import de.wwiser.itemfilter.presentation.listener.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.stream.Stream;

@Plugin(name = "HopperItemFilter", version = "1.0-SNAPSHOT")
@ApiVersion(value = ApiVersion.Target.v1_19)
public class HopperItemFilterPlugin extends JavaPlugin {

    private SessionFactory hibernateSessionFactory;
    private Session hibernateSession;

    @Override
    public void onEnable() {
        hibernateSessionFactory = new Configuration()
                .addAnnotatedClass(HopperFilterEntity.class)
                .configure()
                .buildSessionFactory();
        hibernateSession = hibernateSessionFactory.openSession();

        var jpaHopperFilterRepository = new JpaHopperFilterRepository(hibernateSession);
        var hopperFilterService = new CachedBlockingHopperFilterService(jpaHopperFilterRepository);

        var hopperFilterInventoryFactory = new HopperFilterInventoryFactory();
        var hopperFilterViewStateRegistry = new HopperFilterViewStateRegistry();

        var hopperFilterEvaluator = new HopperFilterEvaluatorImpl();

        Stream.of(
                new BlockBreakListener(hopperFilterService, hopperFilterViewStateRegistry),
                new InventoryPickupItemListener(hopperFilterService, hopperFilterEvaluator),
                new InventoryMoveItemListener(hopperFilterService, hopperFilterEvaluator),
                new InventoryCloseListener(hopperFilterService, hopperFilterViewStateRegistry),
                new InventoryClickListener(hopperFilterService, hopperFilterViewStateRegistry),
                new PlayerInteractListener(
                        hopperFilterService,
                        hopperFilterViewStateRegistry,
                        hopperFilterInventoryFactory
                )
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        if (null != hibernateSessionFactory)
            hibernateSessionFactory.close();

        if (null != hibernateSession)
            hibernateSession.close();
    }

}
