import type { Product } from '../../models/Product';

interface ProductCardProps {
  product: Product;
  isInOrder: boolean;
  onAdd: (product: Product) => void;
}

/**
 * Tarjeta de producto individual del menú
 */
export const ProductCard = ({ product, isInOrder, onAdd }: ProductCardProps) => {
  return (
    <div
      data-testid={`product-card-${product.name.replace(/\s+/g, '-').toLowerCase()}`}
      data-product-name={product.name}
      data-product-type={product.type}
      data-is-in-order={isInOrder}
      onClick={() => onAdd(product)}
      className={`
        group p-4 rounded-2xl cursor-pointer transition-all flex flex-col gap-4
        ${
          isInOrder
            ? 'glass-panel-dark border-primary/40 bg-white/10 ring-1 ring-primary/20'
            : 'glass-panel-dark hover:border-primary/60'
        }
      `}
    >
      {/* Icon */}
      <div className="flex items-center justify-between">
        <div className="size-12 bg-primary/10 rounded-xl flex items-center justify-center">
          <span className="material-symbols-outlined text-primary text-2xl">
            restaurant_menu
          </span>
        </div>
        {isInOrder && (
          <span data-testid="product-added-badge" className="bg-primary/20 text-primary text-xs px-2 py-1 rounded-full font-bold">
            Agregado
          </span>
        )}
      </div>

      {/* Info */}
      <div>
        <h3 data-testid="product-name" className="text-white-text font-bold mb-1">{product.name}</h3>
        {product.description && (
          <p className="text-silver-text text-xs">{product.description}</p>
        )}
      </div>

      {/* Add Button */}
      <button 
        data-testid={`add-product-btn-${product.name.replace(/\s+/g, '-').toLowerCase()}`}
        className="w-full py-2 bg-white/5 hover:bg-primary/20 rounded-lg text-silver-text group-hover:text-primary text-sm font-bold transition-colors flex items-center justify-center gap-2"
      >
        <span className="material-symbols-outlined text-lg">add</span>
        Agregar
      </button>
    </div>
  );
};
