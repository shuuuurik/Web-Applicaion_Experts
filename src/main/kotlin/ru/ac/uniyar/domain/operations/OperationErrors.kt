package ru.ac.uniyar.domain.operations

class AuthenticationError : Exception()

class RequestFetchError(message: String) : RuntimeException(message)
