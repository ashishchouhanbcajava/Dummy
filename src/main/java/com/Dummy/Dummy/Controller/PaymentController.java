package com.Dummy.Dummy.Controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Dummy.Dummy.beans.PaymentVerificationRequest;
import com.razorpay.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PaymentController {

//	@Value("@{razorpay.key.id}")
	private String key = "rzp_test_T0CLhszzwRpcqW";

//	@Value("@{razorpay.key.secret}")
	private String secret = "lFU2AWETB9VDs4e0q28XKURQ";

	@PostMapping("/createOrder")
	public String createOrder(@RequestParam Integer amount) throws RazorpayException {
		RazorpayClient client = new RazorpayClient(key, secret);
		JSONObject object = new JSONObject();
		object.put("amount", amount * 100);
		object.put("currency", "INR");

		Order order = client.orders.create(object);
		return order.toString();
	}

	@GetMapping("/getKey")
	public String getKey() {
		return key;
	}

	@GetMapping("getAllOrders")
	public String getAllOrders() throws RazorpayException {
		RazorpayClient client = new RazorpayClient(key, secret);
		JSONObject params = new JSONObject();
		params.put("count", "1");
		List<Order> fetchAll = client.orders.fetchAll(params);
		return fetchAll.toString();
	}

	@PostMapping("/verify")
	public String verifyPayment(@RequestBody PaymentVerificationRequest request) throws RazorpayException {

		String signature = request.getRazorpay_order_id() + "|" + request.getRazorpay_payment_id();
		boolean verifySignature = Utils.verifySignature(signature, request.getRazorpay_signature(), secret);
		if (verifySignature) {
			return "payment successful !";
		}
		return "Payment failed";
	}
}
