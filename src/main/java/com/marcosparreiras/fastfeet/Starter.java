package com.marcosparreiras.fastfeet;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Starter implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // RemitteeEntity remittee = RemitteeEntity.create(
    //   "00444607242",
    //   "marcos parreiras"
    // );

    // System.out.println(remittee.getCpf());
    // System.out.println(remittee.getName());
    // System.out.println(remittee.getCreatedAt());
    // System.out.println(remittee.getId().getValue());
    // System.out.println(remittee.getUpdatedAt());

    // DeliveryManEntity deliveryMan = DeliveryManEntity.create(
    //   "00444607242",
    //   "Marcos Parreias",
    //   "123456"
    // );
    // System.out.println(deliveryMan.getCpf());
    // System.out.println(deliveryMan.getIsAdmin());
    // System.out.println(deliveryMan.getPassword());
    // System.out.println(deliveryMan.getName());
    // System.out.println(deliveryMan.getId().getValue());
  }
}
