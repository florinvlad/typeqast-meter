package typeqast.business.transformer;

import org.springframework.stereotype.Service;
import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;

@Service
public interface AddressMapperService {

    Address toAddress(AddressDTO addressDTO);

    AddressDTO toAddressDTO(Address addressDTO);

}
