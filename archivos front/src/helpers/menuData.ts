import type { Product } from '../models/Product';
import { ProductType } from '../models/Product';

/**
 * Datos de productos del menú gourmet (simulados)
 * En un caso real, esto vendría del backend
 */
export const MENU_PRODUCTS: Product[] = [
  // Bebidas Gourmet
  {
    id: '1',
    name: 'Gin Tonic Premium',
    type: ProductType.DRINK,
    description: 'Gin Hendricks con tónica Fever-Tree y pepino',
  },
  {
    id: '2',
    name: 'Vino Tinto Reserva',
    type: ProductType.DRINK,
    description: 'Rioja Gran Reserva 2015',
  },
  {
    id: '3',
    name: 'Champagne Brut',
    type: ProductType.DRINK,
    description: 'Moët & Chandon Imperial',
  },
  {
    id: '4',
    name: 'Martini Clásico',
    type: ProductType.DRINK,
    description: 'Vodka Grey Goose con vermut seco',
  },
  {
    id: '5',
    name: 'Whisky Old Fashioned',
    type: ProductType.DRINK,
    description: 'Bourbon premium con angostura y naranja',
  },
  {
    id: '6',
    name: 'Agua San Pellegrino',
    type: ProductType.DRINK,
    description: 'Agua mineral italiana con gas',
  },

  // Platos Calientes Gourmet
  {
    id: '7',
    name: 'Risotto de Trufa Negra',
    type: ProductType.HOT_DISH,
    description: 'Arroz carnaroli con trufa negra y parmigiano reggiano',
  },
  {
    id: '8',
    name: 'Solomillo Wellington',
    type: ProductType.HOT_DISH,
    description: 'Filete envuelto en hojaldre con foie gras',
  },
  {
    id: '9',
    name: 'Lubina al Horno',
    type: ProductType.HOT_DISH,
    description: 'Con verduras mediterráneas y aceite de albahaca',
  },
  {
    id: '10',
    name: 'Magret de Pato',
    type: ProductType.HOT_DISH,
    description: 'Con reducción de frutos rojos y puré de manzana',
  },
  {
    id: '11',
    name: 'Ravioli de Langosta',
    type: ProductType.HOT_DISH,
    description: 'Pasta fresca rellena con salsa de azafrán',
  },
  {
    id: '12',
    name: 'Costillar de Cordero',
    type: ProductType.HOT_DISH,
    description: 'Con hierbas provenzales y patatas confitadas',
  },

  // Ensaladas Gourmet
  {
    id: '13',
    name: 'Ensalada de Burrata',
    type: ProductType.COLD_DISH,
    description: 'Con tomates heirloom, rúcula y reducción balsámica',
  },
  {
    id: '14',
    name: 'Carpaccio de Res',
    type: ProductType.COLD_DISH,
    description: 'Con parmesano, rúcula y aceite de trufa',
  },
  {
    id: '15',
    name: 'Tartar de Atún',
    type: ProductType.COLD_DISH,
    description: 'Con aguacate, sésamo y salsa ponzu',
  },
  {
    id: '16',
    name: 'Ensalada Nicoise',
    type: ProductType.COLD_DISH,
    description: 'Con atún fresco, huevo, aceitunas y anchoas',
  },
  
];
