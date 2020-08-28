package com.lti.utils;

import com.lti.model.ProductDetail;

public class ProductUtil {
	private ProductUtil() {

	}

	public static void getDiscountedPriceofProduct(ProductDetail productDetail) {
		if (null != productDetail && productDetail.getDiscount() > 0 && productDetail.getPrice() > 0) {
			productDetail.setDiscountedPrice(
					productDetail.getPrice() - (productDetail.getPrice() * productDetail.getDiscount() / 100));
		}
	}
}
