package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderDishDto;
import fu.rms.entity.OrderDish;
import fu.rms.mapper.OrderDishMapper;
import fu.rms.newDto.mapper.OrderDishOptionMapper;
import fu.rms.newDto.OrderDishOptionDtoNew;
import fu.rms.repository.OrderDishRepository;
import fu.rms.service.IOrderDishService;

@Service
public class OrderDishService implements IOrderDishService {
	
	@Autowired
	OrderDishMapper orderDishMapper;
	
	@Autowired
	OrderDishRepository orderDishRepo;
	
	@Autowired
	OrderDishOptionMapper orderDishOptionMapper;

	@Override
	public List<OrderDishDto> getListOrderDishByOrder(Long orderId) {

		List<OrderDish> listOrderDish = orderDishRepo.findOrderDishByOrder(orderId);
		List<OrderDishDto> listDto = listOrderDish.stream().map(orderDishMapper::entityToDto)
				.collect(Collectors.toList());	
		
		for (int i = 0; i < listOrderDish.size(); i++) {
			List<OrderDishOptionDtoNew> listOrderDishOption = new ArrayList<OrderDishOptionDtoNew>();
			if(listDto.get(i).getOrderDishOptions() != null) {
				
				listOrderDishOption = listOrderDish.get(i).getOrderDishOptions()
				.stream().map(orderDishOptionMapper::entityToDto).collect(Collectors.toList());	;
			}
			listDto.get(i).setOrderDishOptions(listOrderDishOption);
		}
		
		return listDto;
	}

	@Override
	public int insertOrderDish(OrderDishDto dto, Long orderId) {

		int result =  0;
		if(dto != null) {
			result = orderDishRepo.insertOrderDish(orderId, dto.getDish().getDishId(),
					dto.getQuantity(), dto.getSellPrice(), 
					StatusConstant.STATUS_ORDER_DISH_ORDERED);
		}
		return result;

	}

	@Override
	public int updateStatusOrderDish(Long status, Long orderDishId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateQuantityOrderDish(OrderDishDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

}