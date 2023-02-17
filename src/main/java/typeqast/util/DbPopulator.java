package typeqast.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import typeqast.entities.Address;
import typeqast.entities.Client;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.repository.AddressRepository;
import typeqast.repository.ClientRepository;
import typeqast.repository.MeterRepository;
import typeqast.repository.ReadingRepository;

import java.time.Month;

/*
Utility class for pre-populating the db
 */
public class DbPopulator {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    DbPopulator(ClientRepository clientRepository, AddressRepository addressRepository, MeterRepository meterRepository, ReadingRepository readingRepository) {

        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.meterRepository = meterRepository;
        this.readingRepository = readingRepository;

        populateDB();

    }

    public void populateDB() {

        // add clients

        Client client1 = new Client("name1");
        client1 = clientRepository.save(client1);

        Client client2 = new Client("name2");
        client2 = clientRepository.save(client2);

        Client client3 = new Client("name3");
        client3 = clientRepository.save(client3);

        // add addresses

        Address address1 = new Address("country1", "city1", "street1", 1);
        address1.setClient(client1);
        addressRepository.save(address1);

        Address address2 = new Address("country2", "city2", "street2", 2);
        address2.setClient(client2);
        addressRepository.save(address2);

        Address address3 = new Address("country3", "city3", "street3", 3);
        address3.setClient(client3);
        addressRepository.save(address3);

        // add meters

        Meter meter1 = new Meter("meter1");
        meter1.setClient(client1);
        meter1 = meterRepository.save(meter1);

        Meter meter2 = new Meter("meter2");
        meter2.setClient(client2);
        meter2 = meterRepository.save(meter2);

        Meter meter3 = new Meter("meter3");
        meter3.setClient(client3);
        meter3 = meterRepository.save(meter3);

        // add readings

        Reading reading1 = new Reading(2001, Month.JANUARY, 1L);
        reading1.setMeter(meter1);
        readingRepository.save(reading1);

        reading1 = new Reading(2001, Month.FEBRUARY, 1L);
        reading1.setMeter(meter1);
        readingRepository.save(reading1);


        Reading reading2 = new Reading(2002, Month.JANUARY, 11L);
        reading2.setMeter(meter2);
        readingRepository.save(reading2);

        reading2 = new Reading(2002, Month.FEBRUARY, 11L);
        reading2.setMeter(meter2);
        readingRepository.save(reading2);


        Reading reading3 = new Reading(2003, Month.JANUARY, 111L);
        reading3.setMeter(meter3);
        readingRepository.save(reading3);

        reading3 = new Reading(2003, Month.FEBRUARY, 111L);
        reading3.setMeter(meter3);
        readingRepository.save(reading3);

    }


}
