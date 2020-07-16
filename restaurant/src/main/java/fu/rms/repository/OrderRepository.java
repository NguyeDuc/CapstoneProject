package fu.rms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Order;
import fu.rms.newDto.GetByDish;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.table_id = ?1", nativeQuery = true)
	List<Order> getListOrderByTable(Long table_id);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_code = ?1", nativeQuery = true)
	Order getOrderByCode(String orderCode);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_taker_id = ?1", nativeQuery = true)
	List<Order> getListOrderByOrderTaker(Long staffId);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.chef_id = ?1", nativeQuery = true)
	List<Order> getListOrderByChef(Long staffId);
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.cashier_id = ?1", nativeQuery = true)
	List<Order> getListOrderByCashier(Long staffId);
	
	@Query(value="SELECT * FROM orders AS o WHERE o.table_id = ?1 "
			+ "ORDER BY o.order_date DESC LIMIT 1;", nativeQuery = true)
	Order getCurrentOrderByTable(Long tableId);
	
//	@Query(value="SELECT * FROM orders AS o ORDER BY o.order_id DESC LIMIT 1", nativeQuery = true)
//	Order getLastestOrder();
	
	@Query(value="SELECT * FROM ORDERS o WHERE o.order_id = ?1", nativeQuery = true)
	Order getOrderById(Long orderId);
	
	@Query(value="SELECT * FROM ORDERS o ORDER BY o.order_date DESC", nativeQuery = true)
	List<Order> getListOrder();
	
	/*
	 * tạo mới order
	 */
	@Modifying
	@Transactional
	@Query(name="insert.Order", nativeQuery = true)
	int insertOrder(@Param("order_taker_id") Long orderTakerId, @Param("table_id") Long tableId, 
			@Param("status_id") Long statusId, @Param("order_code") String orderCode, @Param("create_by") String createBy);
	
	
	@Query(name="select.GetByDish", nativeQuery = true)
	List<GetByDish> getByDish();
	
	/*
	 * khi order xong
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.status_id = :statusId, o.order_date = :orderDate, o.total_item = :totalItem, o.total_amount = :totalAmount, "
			+ "o.comment = :comment WHERE o.order_id = :orderId", nativeQuery = true)
	int updateSaveOrder(@Param("statusId") Long statusId, @Param("orderDate") Date orderDate, @Param("totalItem") int totalItem,
			@Param("totalAmount") double totalAmount, @Param("comment") String comment, @Param("orderId") Long orderId);
	
	
	/*
	 * khi thay đổi bàn
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.table_id = :table_id, modified_by = :modifiedBy, modified_date = :modifiedDate"
			+ "WHERE o.order_id = :orderId", nativeQuery = true)
	int updateOrderTable(@Param("table_id") Long tableId, @Param("modifiedBy") String modifiedBy, 
			@Param("modifiedDate") Date modifiedDate, @Param("orderId") Long orderId);
	
	/*
	 * thay đổi trạng thái: trả món, justcooked
	 * @param status
	 * @param orderId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.status_id = :statusId WHERE o.order_id = :orderId", nativeQuery = true)
	int updateStatusOrder(@Param("statusId") Long statusId, @Param("orderId") Long orderId);
	

	/*
	 * Ghi chú
	 * @param comment
	 * @param orderId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.comment = :comment WHERE o.order_id = :orderId", nativeQuery = true)
	int updateComment(@Param("comment") String comment, @Param("orderId") Long orderId);
	
	
	/*
	 * hủy order: canceled
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.status_id = :statusId, modified_date = :modifiedDate, modified_by = :modifiedBy, "
			+ "comment = :comment WHERE o.order_id = :order_id", nativeQuery = true)
	int updateCancelOrder(@Param("statusId") Long statusId, @Param("modifiedDate") Date modifiedDate, 
			@Param("modifiedBy") String modifiedBy, @Param("comment") String comment, @Param("order_id") Long orderId);
	
	/*
	 * delete order
	 */
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Orders WHERE o.order_id = :orderId", nativeQuery = true)
	int deleteOrder(@Param("orderId") Long orderId);
	
	/*
	 * thay đổi bếp: bếp nhận order
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.chef_id = :chefId, o.status_id = :statusId WHERE o.order_id = :orderId", nativeQuery = true)
	int updateOrderChef(@Param("chefId") Long chefId,@Param("statusId") Long status, @Param("orderId") Long orderId);
	
	/*
	 * thay đổi về thu ngân: thanh toán
	 */
//	@Modifying
//	@Transactional
//	@Query(value="UPDATE Orders o SET o.cashier_id = :cashier_id, o.status_id = :status WHERE o.order_id = :order_id", nativeQuery = true)
//	int updateOrderCashier(@Param("cashier_id") Long cashierId,@Param("status") Long status, @Param("order_id") Long orderId);
//	
	/*
	 * Thanh toán xong
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.payment_date = :payment_date, o.cashier_id = :cashierId, o.status_id = :statusId, o.time_to_complete = :time_to_complete "
			+ " WHERE o.order_id = :order_id", nativeQuery = true)
	int updatePayOrder(@Param("payment_date") Date paymentDate, @Param("cashierId") Long cashierId, @Param("statusId") Long status,
			@Param("time_to_complete") String timeToComplete, @Param("order_id") Long orderId);
	
	/*
	 * thay đổi về số lượng, giá
	 */
	@Modifying
	@Transactional
	@Query(value="UPDATE Orders o SET o.total_item = :total_item, o.total_amount = :total_amount WHERE o.order_id = :order_id", nativeQuery = true)
	int updateOrderQuantity(@Param("total_item") int totalItem, @Param("total_amount") double totalAmount, @Param("order_id") Long orderId);
	
}
