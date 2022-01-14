package de.wwiser.itemfilter.infra.repository;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import de.wwiser.itemfilter.infra.HopperFilterEntity;
import org.hibernate.Session;

import java.util.List;

@AllArgsConstructor
public class JpaHopperFilterRepository implements HopperFilterRepository {

    private final Session hibernateSession;

    @Override
    public List<HopperFilterEntity> findAll() {
        TypedQuery<HopperFilterEntity> query = hibernateSession.createQuery(
                "SELECT hfe FROM HopperFilterEntity hfe",
                HopperFilterEntity.class
        );
        return query.getResultList();
    }

    @Override
    public void saveFilter(HopperFilterEntity entity) {
        executeInTransaction(() -> {
            if (entity.getId() == null) {
                hibernateSession.persist(entity);
            } else {
                hibernateSession.merge(entity);
            }
        });
    }

    @Override
    public void deleteFilter(int hopperFilterId) {
        executeInTransaction(() -> {
            Query query = hibernateSession.createQuery("DELETE FROM HopperFilterEntity hfe WHERE hfe.id = :id");
            query.setParameter("id", hopperFilterId);
            query.executeUpdate();
        });
    }

    private void executeInTransaction(Runnable callback) {
        EntityTransaction transaction = hibernateSession.getTransaction();

        try {
            transaction.begin();
            callback.run();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

}
