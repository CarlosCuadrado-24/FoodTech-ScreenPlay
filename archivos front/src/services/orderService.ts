import { apiClient } from './apiClient';
import type {
  CreateOrderRequest,
  CreateOrderResponse,
  OrderStatusResponse,
} from '../models/Order';

/**
 * Servicio para manejar operaciones de pedidos
 */
class OrderService {
  /**
   * Crea un nuevo pedido
   */
  async createOrder(request: CreateOrderRequest): Promise<CreateOrderResponse> {
    return apiClient.post<CreateOrderRequest, CreateOrderResponse>(
      '/api/orders',
      request
    );
  }

  /**
   * Obtiene el estado de un pedido
   */
  async getOrderStatus(orderId: number): Promise<OrderStatusResponse> {
    return apiClient.get<OrderStatusResponse>(`/api/orders/${orderId}/status`);
  }
}

export const orderService = new OrderService();
