package com.Jobportal.utility;

public class Data {
	public static String getMessageBody(String otp, String name) {
		return  "<!DOCTYPE html>\n" +
		        "<html lang=\"en\">\n" +
		        "<head>\n" +
		        "    <meta charset=\"UTF-8\">\n" +
		        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
		        "    <title>OTP Email</title>\n" +
		        "    <style>\n" +
		        "        body {\n" +
		        "            font-family: Arial, sans-serif;\n" +
		        "            background-color: #f9f9f9;\n" +
		        "            margin: 0;\n" +
		        "            padding: 0;\n" +
		        "        }\n" +
		        "        .email-container {\n" +
		        "            max-width: 600px;\n" +
		        "            margin: 20px auto;\n" +
		        "            background-color: #ffffff;\n" +
		        "            border: 1px solid #e0e0e0;\n" +
		        "            border-radius: 8px;\n" +
		        "            overflow: hidden;\n" +
		        "        }\n" +
		        "        .header {\n" +
		        "            background-color: #4caf50;\n" +
		        "            color: #ffffff;\n" +
		        "            padding: 20px;\n" +
		        "            text-align: center;\n" +
		        "            font-size: 24px;\n" +
		        "        }\n" +
		        "        .content {\n" +
		        "            padding: 20px;\n" +
		        "            color: #333333;\n" +
		        "        }\n" +
		        "        .otp {\n" +
		        "            font-size: 28px;\n" +
		        "            font-weight: bold;\n" +
		        "            color: #4caf50;\n" +
		        "            text-align: center;\n" +
		        "            margin: 20px 0;\n" +
		        "        }\n" +
		        "        .footer {\n" +
		        "            background-color: #f1f1f1;\n" +
		        "            color: #666666;\n" +
		        "            text-align: center;\n" +
		        "            padding: 10px;\n" +
		        "            font-size: 14px;\n" +
		        "        }\n" +
		        "    </style>\n" +
		        "</head>\n" +
		        "<body>\n" +
		        "    <div class=\"email-container\">\n" +
		        "        <div class=\"header\">\n" +
		        "            Your OTP Verification Code\n" +
		        "        </div>\n" +
		        "        <div class=\"content\">\n" +
		        "            <p>Dear "+name+",</p>\n" +
		        "            <p>We received a request to verify your account. Please use the One-Time Password (OTP) below to complete the process:</p>\n" +
		        "            <div class=\"otp\">" + otp +"</div>\n" +
		        "            <p>If you did not request this, please ignore this email or contact support if you have concerns.</p>\n" +
		        "            <p>Thank you, <br> The Support Team</p>\n" +
		        "        </div>\n" +
		        "        <div class=\"footer\">\n" +
		        "            © 2024 JobAlchemy. All rights reserved.\n" +
		        "        </div>\n" +
		        "    </div>\n" +
		        "</body>\n" +
		        "</html>";

	}
}
