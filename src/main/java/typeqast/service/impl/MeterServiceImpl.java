package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.response.MeterResponse;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.service.MeterService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class MeterServiceImpl implements MeterService {

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public MeterResponse addMeter(Meter meter, BigInteger clientId) {
        MeterResponse meterResponse = new MeterResponse();

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (resultClient.isPresent()) {
            try {
                Meter saveMeter = new Meter(meter.getName());
                saveMeter.setClient(resultClient.get());
                meterResponse.setMeter(meterRepository.save(saveMeter));
                meterResponse.setStatus(HttpStatus.CREATED);
            } catch (DataIntegrityViolationException dive) {
                meterResponse.setStatus(HttpStatus.BAD_REQUEST);
            }
        } else {
            meterResponse.setStatus(HttpStatus.NOT_FOUND);
        }


        return meterResponse;
    }

    @Override
    public MeterResponse updateMeter(Meter updateMeter, BigInteger clientId) {

        BigInteger id = updateMeter.getId();

        MeterResponse meterResponse = new MeterResponse();

        if (id != null) {
            Optional<Client> readClient = clientRepository.findOne(Example.of(new Client(clientId)));
            Optional<Meter> readMetter = meterRepository.findOne(Example.of(new Meter(id)));

            if (readMetter.isPresent() && (readClient.isPresent())) {

                try {
                    updateMeter.setClient(readClient.get());
                    meterResponse.setMeter(meterRepository.save(updateMeter));
                    meterResponse.setStatus(HttpStatus.OK);
                } catch (DataIntegrityViolationException dive) {
                    meterResponse.setStatus(HttpStatus.BAD_REQUEST);
                }

            } else {
                meterResponse.setStatus(HttpStatus.NOT_FOUND);
            }
        } else {
            meterResponse.setStatus(HttpStatus.BAD_REQUEST);
        }

        return meterResponse;
    }

    @Override
    public List<Meter> getMeters(BigInteger meterId) {

        List<Meter> meters = meterRepository.findAll(Example.of(new Meter(meterId)));

        return meters;
    }

}
