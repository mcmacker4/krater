package com.mcmacker4.krater.server.exceptions



class InvalidRequestMethodException(value: String, cause: Exception) : Exception("Invalid Request Method: $value", cause) 

class BadRequestException(message: String, cause: Exception) : Exception(message, cause)