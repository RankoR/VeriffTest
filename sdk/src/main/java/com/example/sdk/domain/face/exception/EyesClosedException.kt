package com.example.sdk.domain.face.exception

class EyesClosedException(
    leftOpenProbability: Float,
    rightOpenProbability: Float
) : Exception("Eyes are closed: left=$leftOpenProbability, right=$rightOpenProbability")
