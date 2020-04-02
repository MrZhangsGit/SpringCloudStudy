package my.elasticsearch.po;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Auther: jorden
 * @Date: 2018/4/27 14:13
 * @Description: 分页查询MO
 */
@Data
public  class PageableMO<T> implements Pageable {
	/**
	 * 当前页(默认第0页
	 */
	private int pageNumber = 0;
	/**
	 * 每页条数(默认每页15条
	 */
	private int pageSize = 15;
	/**
	 * 排序:升序或降序
	 */
	private String sort;
	/**
	 * 排序字段
	 */
	private String property;
	/**
	 * 总页数
	 */
	private int totalPages;
	/**
	 * 总记录数
	 */
	private long total;
	private List<T> content;

	public PageableMO() {

	}

	public PageableMO(int totalPages, long total, List<T> content) {
		this.totalPages = totalPages;
		this.total = total;
		this.content = content;
	}

	@Override
	public int getOffset() {
		return this.pageNumber * this.pageSize;
	}

	@Override
	public Sort getSort() {
		if (StringUtils.hasText(sort) && StringUtils.hasText(property)) {
			return new Sort(Direction.fromString(this.sort), property);
		}
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return (this.pageNumber > 0);
	}

	@Override
	public Pageable next() {
		return new PageRequest(this.pageNumber + 1, this.pageSize, getSort());
	}

	@Override
	public Pageable previousOrFirst() {
		return ((hasPrevious()) ? new PageRequest(this.pageNumber - 1, this.pageSize, getSort()) : this);
	}

	@Override
	public Pageable first() {
		return new PageRequest(0, this.pageSize, getSort());
	}
}
