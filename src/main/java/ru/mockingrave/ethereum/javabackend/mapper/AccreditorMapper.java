package ru.mockingrave.ethereum.javabackend.mapper;

import org.mapstruct.Mapper;
import ru.mockingrave.ethereum.javabackend.dto.IpfsAuthorityDto;
import ru.mockingrave.ethereum.javabackend.ipfs.model.Accreditor;
import ru.mockingrave.ethereum.javabackend.mapper.base.BaseMapper;
import ru.mockingrave.ethereum.javabackend.mapper.base.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface AccreditorMapper extends BaseMapper<IpfsAuthorityDto, Accreditor> {

//    @Mapping(source = "sourceAccreditor.name", target = "sourceAccreditorName")
//    @Mapping(source = "sourceAccreditor.ipfsHash", target = "sourceAccreditorIpfsHash")
//    @Mapping(source = "sourceAccreditor.ethAddress", target = "sourceAccreditorEthAddress")
//    Accreditor toEntity(IpfsAuthorityDto dto);
//
//    @Mapping(source = "sourceAccreditorName", target = "sourceAccreditor.name")
//    @Mapping(source = "sourceAccreditorIpfsHash", target = "sourceAccreditor.ipfsHash")
//    @Mapping(source = "sourceAccreditorEthAddress", target = "sourceAccreditor.ethAddress")
//    IpfsAuthorityDto toDto(Accreditor entity);

}
