package typeqast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import typeqast.entities.Client;
import typeqast.entities.Meter;
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
    public Meter addMeter(Meter meter, BigInteger clientId) {
        Meter saveMeter = null;

        Optional<Client> resultClient = clientRepository.findOne(Example.of(new Client(clientId)));

        if (resultClient.isPresent()) {
            saveMeter = new Meter(meter.getName());
            saveMeter.setClient(resultClient.get());
            saveMeter = meterRepository.save(saveMeter);
        }

        return saveMeter;
    }

    @Override
    public Meter updateMeter(Meter updateMeter, BigInteger clientId) {

        BigInteger id = updateMeter.getId();

        Meter saveMeter = null;

        if (id != null) {
            Optional<Client> readClient = clientRepository.findOne(Example.of(new Client(clientId)));
            Optional<Meter> readMetter = meterRepository.findOne(Example.of(new Meter(id)));

            if (readMetter.isPresent() && (readClient.isPresent())) {

                updateMeter.setClient(readClient.get());
                saveMeter = meterRepository.save(updateMeter);

            }
        }

        return saveMeter;
    }

    @Override
    public List<Meter> getMeters(BigInteger meterId) {

        List<Meter> meters = meterRepository.findAll(Example.of(new Meter(meterId)));

        return meters;
    }

}
