package com.tevore.ordersystem.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

    private Promotion promo;

    public BigDecimal validateAndApplyPromotion(BigDecimal cost, Integer quantity, String promotion) {

        if (Optional.ofNullable(promotion).isPresent()) {

            Optional<Promotion> prom1 = Arrays.stream(Promotion.values())
                    .filter(p -> p.getName().equalsIgnoreCase(promotion))
                    .findFirst();

            if (prom1.isPresent()) {
                LOGGER.info("Promotion found!");

                BigDecimal promotionAmount = BigDecimal.valueOf(0);

                switch (prom1.get()) {
                    case BOGO:
                        LOGGER.info("Applying BOGO promotion");
                        promotionAmount = applyBOGOPromotion(cost, quantity);
                        break;
                    case B2GO:
                        LOGGER.info("Applying B2GO promotion");
                        promotionAmount = applyB2GOPromotion(cost, quantity);
                        break;
                    default: //useless since we do a precheck....
                        LOGGER.info("Promotion code {} was invalid", promotion);
                }


                return promotionAmount;
            } else {
                LOGGER.info("Promotion {} was invalid", promotion);
            }
        }
        return cost.multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal applyBOGOPromotion(BigDecimal cost, Integer quantity) {

        if(quantity%2 == 0) {
            return cost.multiply(BigDecimal.valueOf(quantity/2));
        } else {
            return cost.multiply(BigDecimal.valueOf((quantity/2) + 1));
        }

    }

    private BigDecimal applyB2GOPromotion(BigDecimal cost, Integer quantity) {
        return cost.multiply(BigDecimal.valueOf(quantity - (quantity/3)));
    }
}
