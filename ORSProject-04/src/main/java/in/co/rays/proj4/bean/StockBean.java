package in.co.rays.proj4.bean;

public class StockBean extends BaseBean {

	private Long stockId;
	private String stockName;
	private Double price;
	private Integer quantity;

	@Override
	public String getKey() {
		return id + "";
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String getValue() {
		return stockName;
	}

}
