package com.tevore.ordersystem.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/**
 * The promotion service is dedicated to two major functions:
 * 1. Checking to see if the promotion exists in the Promotion enum
 * 2. If the promotion exists, it applies the correct promotion calculation
 *    else, it returns the default cost calculation ( i.e. full price )
 */
@Service
public class PromotionService {

    private final Logger LOGGER = LoggerFactory.getLogger(PromotionService.class);

    public BigDecimal validateAndApplyPromotion(BigDecimal cost, Integer quantity, String promotion) {

        if (Optional.ofNullable(promotion).isPresent()) {

            Optional<Promotion> optPromo = Arrays.stream(Promotion.values())
                    .filter(p -> p.getName().equalsIgnoreCase(promotion))
                    .findFirst();

            if (optPromo.isPresent()) {
                LOGGER.info("Promotion found!");

                BigDecimal promotionAmount = BigDecimal.valueOf(0);

                switch (optPromo.get()) {
                    case BOGO:
                        LOGGER.info("Applying BOGO promotion");
                        promotionAmount = applyBOGOPromotion(cost, quantity);
                        break;
                    case B2GO:
                        LOGGER.info("Applying B2GO promotion");
                        promotionAmount = applyB2GOPromotion(cost, quantity);
                        break;
                    default: //just in case it gets down here
                        LOGGER.info("Promotion code {} was invalid", promotion);
                }

                return promotionAmount;
            } else {
                LOGGER.info("Promotion {} was invalid", promotion);
            }
        }
        LOGGER.info("No promotion found.");
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
