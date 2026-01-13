/**
 * Estado de una mesa en el restaurante
 */
export const TableStatus = {
  DISPONIBLE: 'DISPONIBLE',
  OCUPADA: 'OCUPADA',
} as const;

export type TableStatus = (typeof TableStatus)[keyof typeof TableStatus];

/**
 * Modelo de mesa
 */
export interface Table {
  id: string;
  number: string;
  status: TableStatus;
  activeOrderId?: number;
}
