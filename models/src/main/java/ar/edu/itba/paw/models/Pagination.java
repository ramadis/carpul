package ar.edu.itba.paw.models;

public class Pagination {
	private Integer page = 0;
	private Integer per_page = 5;
	
	public Pagination(Integer page, Integer per_page) {
		this.setPage(page);
		this.setPer_page(per_page);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPer_page() {
		return per_page;
	}

	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}
}
