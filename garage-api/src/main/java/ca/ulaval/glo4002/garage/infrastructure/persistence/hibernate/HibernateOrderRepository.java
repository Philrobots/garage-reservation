package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerController;
import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerService;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import ca.ulaval.glo4002.garage.domain.orders.OrderRepository;
import ca.ulaval.glo4002.garage.domain.orders.Part;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.OrderEntity;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.mapper.EntityMapper;

import javax.persistence.EntityManager;

public class HibernateOrderRepository implements OrderRepository {
    //commentaire
    // Order et Appointment sont dans des dto avec des mappers parce que je me suis rappeler que vous disiez que c'était
    // pas nécessaire d'en faire après les avoirs toutes implémentés.... j'ai est laissé comme ca parce que ca me mettais off
    // de toute supprimer mais je comprends que c'est pas normal que Part aille pas de Dto tandis que order et appointment oui
    // j'avais pas encore fait le dto de Part avant de m'en rendre compte

    private final EntityMapper entityMapper = new EntityMapper();
    private EntityManagerService entityManagerService;

    public HibernateOrderRepository(EntityManagerService entityManagerService) {
        this.entityManagerService = entityManagerService;
    }

    public HibernateOrderRepository() {
        entityManagerService = new EntityManagerController();
    };

    @Override
    public void save(Order order) {
        // TODO lab
        EntityManager entityManager = entityManagerService.getEntityManger();
        try {
            OrderEntity orderEntity = entityMapper.convertOrderToEntity(order);
            entityManager.persist(orderEntity);
            saveParts(orderEntity, entityManager);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private void saveParts(OrderEntity order, EntityManager entityManager) {
        order.getParts().forEach(part -> saveOrderPart(order, entityManager, part));
    }

    private void saveOrderPart(OrderEntity order, EntityManager entityManager, Part part) {
        try {
            part.setOrder_id(order.getId());
            entityManager.persist(part);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    @Override
    public List<Order> findAll() {
        // TODO lab
        EntityManager entityManager = entityManagerService.createEntityManager();
        List<OrderEntity> orders = getOrders(entityManager);
        List<Part> parts = getParts(entityManager);
        return convertOrderEntitiesToOrders(orders, parts);
    }

    private List<OrderEntity> getOrders(EntityManager entityManager) {
        return entityManager.createQuery("SELECT orderEntity from OrderEntity orderEntity").getResultList();
    }

    private List<Part> getParts(EntityManager entityManager) {
        return entityManager.createQuery("select part from Part part").getResultList();
    }

    private List<Order> convertOrderEntitiesToOrders(List<OrderEntity> orderEntities, List<Part> parts) {
        return orderEntities.stream().map(orderEntity -> entityMapper.convertEntityToOrder(orderEntity, parts)).collect(Collectors.toList());
    }
}
