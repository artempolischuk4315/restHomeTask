package polishchuk.mapper;

public interface Mapper<D,E> {

    public D mapEntityToDto(E e);

    public E mapDtoToEntity(D d);

}