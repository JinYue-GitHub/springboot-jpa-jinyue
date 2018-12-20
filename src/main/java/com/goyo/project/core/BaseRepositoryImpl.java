package com.goyo.project.core;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.DefaultJpaEntityMetadata;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.JpaPersistableEntityInformation;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.goyo.project.core.QueryHints.NoHints;

/**
 * @author: JinYue
 * @ClassName: BaseRepositoryImpl 
 * @Description: 通用仓库实现
 */
@Repository
public class BaseRepositoryImpl<T,ID extends Serializable> implements BaseRepository<T,ID> {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	private Class<T> clazz;
	
//	private JpaEntityMetadata<T> metadata;

	private @Nullable CrudMethodMetadata metadata;

	private JpaEntityInformation<T, ?> entityInformation=null;
	
	public BaseRepositoryImpl() {
		//子类的构造方法会默认调用父类空参  super(), 所以this代表子类对象
//        Class childClazz = this.getClass(); //子类字节码对象
        //得到父类的字节码BaseDaoImpl的字节码 ， 这份字节码上带有泛型数据
        /**
         * 虽然这个方法，返回值说的是Type ，
         * 但是其实返回的是ParameterizedType的实现类类型。
         * 所以我们使用ParameterizedTypeImpl接口来接收。
         */
//        Type[] genericInterfaces = childClazz.getGenericInterfaces();
//        TypeVariable[] typeParameters = childClazz.getTypeParameters();
        
        
       /* ParameterizedType parameterizedType = (ParameterizedType) BaseRepositoryImpl.class.getGenericInterfaces()[0];
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument);
        }*/

        
        
//        System.out.println(genericInterfaces);
//        System.out.println(typeParameters);
//        Type[] actualTypeArguments = childClazz.getActualTypeArguments();
//        ParameterizedType genericSuperclass = (ParameterizedType) childClazz.getGenericSuperclass();
        //获取这样可以得到泛型了
        //因为泛型可能不止一个,所以返回的是数组,所以我们取第一个,
//        Type type = genericSuperclass.getActualTypeArguments()[0];
//        clazz = (Class<T>) actualTypeArguments[0];
        
        this.metadata = clazz==null?null:(CrudMethodMetadata) new DefaultJpaEntityMetadata(clazz);
        
        this.entityInformation = clazz==null?null:getEntityInformation(clazz, entityManager);
        
	}
	

   
    @Transactional
    @Override
    public T save(T entity){
        try {
            entityManager.persist(entity);
        }catch (Exception e){
            System.out.println("===============保存出错===============");
            throw e;
        }
        return entity;
    }
    @Transactional
    @Override
    public Object findById(Object o,Object id) {
        return entityManager.find(o.getClass(),id);
    }
    @Transactional
    @Override
    public List<T> findBysql(String tablename, String filed, Object o ) {
        String sql="from "+tablename+" u WHERE u."+filed+"=?";
        System.out.println(sql+"===============sql语句===============");
        Query query=entityManager.createQuery(sql);
        query.setParameter(1,o);
        List<T> list= query.getResultList();
        entityManager.close();
        return list;
    }

    @Transactional
    @Override
    public Object findObjiectBysql(String tablename, String filed, Object o) {
        String sql="from "+tablename+" u WHERE u."+filed+"=?";
        System.out.println(sql+"===============sql语句===============");
        Query query=entityManager.createQuery(sql);
        query.setParameter(1,o);

        entityManager.close();
        return query.getSingleResult();
    }
    @Transactional
    @Override
    public List<T> findByMoreFiled(String tablename,LinkedHashMap<String,Object> map) {
        String sql="from "+tablename+" u WHERE ";
        Set<String> set=null;
        set=map.keySet();
        List<String> list=new ArrayList<>(set);
        List<Object> filedlist=new ArrayList<>();
        for (String filed:list){
            sql+="u."+filed+"=? and ";
            filedlist.add(filed);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println(sql+"===============sql语句===============");
        Query query=entityManager.createQuery(sql);
        for (int i=0;i<filedlist.size();i++){
            query.setParameter(i+1,map.get(filedlist.get(i)));
        }
        List<T> listRe= query.getResultList();
        entityManager.close();
        return listRe;
    }
    @Transactional
    @Override
    public List<T> findByMoreFiledpages(String tablename,LinkedHashMap<String,Object> map,int start,int pageNumber) {
        String sql="from "+tablename+" u WHERE ";
        Set<String> set=null;
        set=map.keySet();
        List<String> list=new ArrayList<>(set);
        List<Object> filedlist=new ArrayList<>();
        for (String filed:list){
            sql+="u."+filed+"=? and ";
            filedlist.add(filed);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println(sql+"===============sql语句===============");
        Query query=entityManager.createQuery(sql);
        for (int i=0;i<filedlist.size();i++){
            query.setParameter(i+1,map.get(filedlist.get(i)));
        }
        query.setFirstResult((start-1)*pageNumber);
        query.setMaxResults(pageNumber);
        List<T> listRe= query.getResultList();
        entityManager.close();
        return listRe;
    }
    @Transactional
    @Override
    public List<T> findpages(String tablename, String filed, Object o, int start, int pageNumer) {
        String sql="from "+tablename+" u WHERE u."+filed+"=?";
        System.out.println(sql+"===============page--sql语句===============");
        List<T> list=new ArrayList<>();
        try {
            Query query=entityManager.createQuery(sql);
            query.setParameter(1,o);
            query.setFirstResult((start-1)*pageNumer);
            query.setMaxResults(pageNumer);
            list= query.getResultList();
            entityManager.close();
        }catch (Exception e){
            System.out.println("===============分页错误===============");
        }

        return list;
    }
    @Transactional
    @Override
    public boolean update(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
        } catch (Exception e) {
            System.out.println("===============更新出错===============");
        }
        return flag;
    }
    @Transactional
    @Override
    public Integer updateMoreFiled(String tablename, LinkedHashMap<String, Object> map) {
        String sql="UPDATE "+tablename+" AS u SET ";
        Set<String> set=null;
        set=map.keySet();
        List<String> list=new ArrayList<>(set);
        for (int i=0;i<list.size()-1;i++){
            if (map.get(list.get(i)).getClass().getTypeName()=="java.lang.String"){
                System.out.println("-*****"+map.get(list.get(i))+"==============="+list.get(i));
                sql+="u."+list.get(i)+"='"+map.get(list.get(i))+"' , ";
            }else {
                sql+="u."+list.get(i)+"="+map.get(list.get(i))+" , ";
            }
        }
        sql=sql.substring(0,sql.length()-2);
        sql+=" where u.id=?0 ";
        System.out.println(sql+"===============sql语句===============");
        int resurlt=0;
        try {
            Query query=entityManager.createQuery(sql);
//            query.setParameter(1,map.get("id"));
            query.setParameter("0", map.get("id"));
            resurlt= query.executeUpdate();
        }catch (Exception e){
            System.out.println("===============更新出错===============");
            e.printStackTrace();

        }
        return resurlt;
    }
    
    @Transactional
    @Override
    public boolean excuteSql(String sql) {
        boolean flag=false;
        try {
        	Query query=entityManager.createQuery(sql);
        	query.executeUpdate();
            flag=true;
        }catch (Exception e){
            System.out.println("===============DML出错===============");
        }
        return flag;
    }

    @Transactional
    @Override
    public boolean delete(T entity) {
        boolean flag=false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag=true;
        }catch (Exception e){
            System.out.println("===============删除出错===============");
        }
        return flag;
    }
    @Transactional
    @Override
    public Object findCount(String tablename, LinkedHashMap<String, Object> map) {
        String sql="select count(u) from "+tablename+" u WHERE ";
        Set<String> set=null;
        set=map.keySet();
        List<String> list=new ArrayList<>(set);
        List<Object> filedlist=new ArrayList<>();
        for (String filed:list){
            sql+="u."+filed+"=? and ";
            filedlist.add(filed);
        }
        sql=sql.substring(0,sql.length()-4);
        System.out.println(sql+"===============sql语句===============");
        Query query=entityManager.createQuery(sql);
        for (int i=0;i<filedlist.size();i++){
            query.setParameter(i+1,map.get(filedlist.get(i)));
        }
        return query.getSingleResult();
    }
    //**********************源码copy做来的实现
    /* 
     * (non-Javadoc) 
     *  
     * @see org.springframework.data.jpa.repository.JpaRepository#findAll() 
     */  
    @Transactional
    public List<T> findAll(Class<T> clazz) {  
    	this.clazz = clazz;
        return getQuery(null, (Sort) null).getResultList();  
    }  
    /* 
     * (non-Javadoc) 
     *  
     * @see 
     * org.springframework.data.repository.PagingAndSortingRepository#findAll 
     * (org.springframework.data.domain.Pageable) 
     */  
    @Transactional
    public Page<T> findAll(Pageable pageable,Class<T> clazz) {  
    	this.clazz = clazz;
        if (null == pageable) {  
            return new PageImpl<T>(findAll(clazz));  
        }  
  
        return findAll(null, pageable,clazz);  
    }  
    
    /*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Pageable)
	 */
    @Transactional
	public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable,Class<T> clazz) {
		this.clazz = clazz;
		TypedQuery<T> query = getQuery(spec, pageable);
		return isUnpaged(pageable) ? new PageImpl<T>(query.getResultList())
				: readPage(query, clazz, pageable, spec);
	}
    @Transactional
	protected <S extends T> Page<S> readPage(TypedQuery<S> query, final Class<S> domainClass, Pageable pageable,
			@Nullable Specification<S> spec) {

		if (pageable.isPaged()) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return PageableExecutionUtils.getPage(query.getResultList(), pageable,
				() -> executeCountQuery(getCountQuery(spec, domainClass)));
	}
	
    @Transactional
	private static Long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query, "TypedQuery must not be null!");

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}
    @Transactional
	private static boolean isUnpaged(Pageable pageable) {
		return pageable.isUnpaged();
	}
    
    /**
	 * Creates a new {@link TypedQuery} from the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param pageable must not be {@literal null}.
	 * @return
	 */
    @Transactional
	protected TypedQuery<T> getQuery(@Nullable Specification<T> spec, Pageable pageable) {

		Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
		return getQuery(spec, clazz, sort);
	}

	/**
	 * Creates a new {@link TypedQuery} from the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param pageable must not be {@literal null}.
	 * @return
	 */
    @Transactional
	protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass,
			Pageable pageable) {

		Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
		return getQuery(spec, domainClass, sort);
	}

	/**
	 * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
	 *
	 * @param spec can be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return
	 */
    @Transactional
	protected TypedQuery<T> getQuery(@Nullable Specification<T> spec, Sort sort) {
		return getQuery(spec, clazz, sort);
	}

	/**
	 * Creates a {@link TypedQuery} for the given {@link Specification} and {@link Sort}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return
	 */
    @Transactional
	protected <S extends T> TypedQuery<S> getQuery(@Nullable Specification<S> spec, Class<S> domainClass, Sort sort) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<S> query = builder.createQuery(domainClass);

		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);
		query.select(root);

		if(sort!=null){
			if (sort.isSorted()) {
				query.orderBy(toOrders(sort, root, builder));
			}
		}
		return applyRepositoryMethodMetadata(entityManager.createQuery(query));
	}

	/**
	 * Creates a new count query for the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @return
	 * @deprecated override {@link #getCountQuery(Specification, Class)} instead
	 */
    @Transactional
	@Deprecated
	protected TypedQuery<Long> getCountQuery(@Nullable Specification<T> spec) {
		return getCountQuery(spec, clazz);
	}

	/**
	 * Creates a new count query for the given {@link Specification}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @return
	 */
    @Transactional
	protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<S> root = applySpecificationToCriteria(spec, domainClass, query);

		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}

		// Remove all Orders the Specifications might have applied
		query.orderBy(Collections.<Order> emptyList());

		return entityManager.createQuery(query);
	}

	/**
	 * Applies the given {@link Specification} to the given {@link CriteriaQuery}.
	 *
	 * @param spec can be {@literal null}.
	 * @param domainClass must not be {@literal null}.
	 * @param query must not be {@literal null}.
	 * @return
	 */
    @Transactional
	private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass,
			CriteriaQuery<S> query) {

		Assert.notNull(domainClass, "Domain class must not be null!");
		Assert.notNull(query, "CriteriaQuery must not be null!");

		Root<U> root = query.from(domainClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}
    @Transactional
	private <S> TypedQuery<S> applyRepositoryMethodMetadata(TypedQuery<S> query) {

		if (metadata == null) {
			return query;
		}

		LockModeType type = metadata.getLockModeType();
		
		TypedQuery<S> toReturn = type == null ? query : query.setLockMode(type);

		applyQueryHints(toReturn);

		return toReturn;
	}
    @Transactional
	private void applyQueryHints(Query query) {

		for (Entry<String, Object> hint : getQueryHints().withFetchGraphs(entityManager)) {
			query.setHint(hint.getKey(), hint.getValue());
		}
	}
	
	
	/**
	 * Returns {@link QueryHints} with the query hints based on the current {@link CrudMethodMetadata} and potential
	 * {@link EntityGraph} information.
	 *
	 * @return
	 */
    @Transactional
	protected QueryHints getQueryHints() {
		return metadata == null ? NoHints.INSTANCE : DefaultQueryHints.of(entityInformation, metadata);
	}
	
	
	
	
	
	
	


	/**
	 * Creates a {@link JpaEntityInformation} for the given domain class and {@link EntityManager}.
	 * 
	 * @param domainClass must not be {@literal null}.
	 * @param em must not be {@literal null}.
	 * @return
	 */
    @Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> JpaEntityInformation<T, ?> getEntityInformation(Class<T> domainClass, EntityManager em) {

		Assert.notNull(domainClass, "Domain class must not be null!");
		Assert.notNull(em, "EntityManager must not be null!");

		Metamodel metamodel = em.getMetamodel();

		if (Persistable.class.isAssignableFrom(domainClass)) {
			return new JpaPersistableEntityInformation(domainClass, metamodel);
		} else {
			return new JpaMetamodelEntityInformation(domainClass, metamodel);
		}
	}



	public EntityManager getEntityManager() {
		return entityManager;
	}



	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}



	public Class<T> getClazz() {
		return clazz;
	}



	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}



	public CrudMethodMetadata getMetadata() {
		return metadata;
	}



	public void setMetadata(CrudMethodMetadata metadata) {
		this.metadata = metadata;
	}



	public JpaEntityInformation<T, ?> getEntityInformation() {
		return entityInformation;
	}



	public void setEntityInformation(JpaEntityInformation<T, ?> entityInformation) {
		this.entityInformation = entityInformation;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
   /* private Class<T> getDomainClass() {  
        return clazz;  
    }  
    
    private JpaEntityInformation getJpaPersistableEntityInformation() {
    	Metamodel metamodel = entityManager.getMetamodel();
    	if (Persistable.class.isAssignableFrom(getDomainClass())) {
    		return new JpaPersistableEntityInformation(getDomainClass(), metamodel);
    	} else {
    		return new JpaMetamodelEntityInformation(getDomainClass(), metamodel);
    	}
	}*/
    
    /** 
     * Applies the given {@link Specification} to the given 
     * {@link CriteriaQuery}. 
     *  
     * @param spec 
     *            can be {@literal null}. 
     * @param query 
     *            must not be {@literal null}. 
     * @return 
     */  
//    private <S> Root<T> applySpecificationToCriteria(Specification<T> spec,  
//            CriteriaQuery<S> query) {  
//  
//        Assert.notNull(query);  
//        Root<T> root = query.from(getDomainClass());  
//  
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();  
//  
//        // 增加了删除条件判断，从而将被逻辑删除的数据过滤掉  
//        Predicate deletedPredicate = null;  
//        if (BaseDefaultModel.class.isAssignableFrom(getDomainClass())) {  
//            Path<Boolean> deletedPath = root.<Boolean> get(DELETEED_FIELD);  
//            deletedPredicate = builder.isFalse(deletedPath);  
//        }  
//          
//        if (spec == null) {  
//            // 没有其它条件的时候只判断deleted字段  
//            query.where(deletedPredicate);  
//              
//            return root;  
//        }  
//          
//        Predicate predicate = spec.toPredicate(root, query, builder);  
//  
//        if (predicate != null) {  
//            // 存在其它条件的时候还需要组合一下 deleted 条件  
//            if (null != deletedPredicate) {  
//                predicate = builder.and(predicate, deletedPredicate);  
//            }  
//            query.where(predicate);  
//        }  
//  
//        return root;  
//    }  
    
    
    
    
}