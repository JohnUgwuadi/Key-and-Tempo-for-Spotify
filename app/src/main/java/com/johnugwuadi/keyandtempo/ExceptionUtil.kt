package com.johnugwuadi.keyandtempo

import com.johnugwuadi.keyandtempo.data.repositories.Result
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> handleRetrofitErrors(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (ex: SocketTimeoutException) {
        Result.Error(SocketTimeoutException("Unable to connect with server. Timed out."))
    } catch (ex: ConnectException) {
        Result.Error(ConnectException("Unable to connect with server."))
    } catch (ex: HttpException) {
        when (ex.code()) {
            500 -> Result.Error(Exception("Server error. Unable to connect."))
            else -> Result.Error(Exception("${ex.code()} error."))
        }
    } catch (ex: UnknownHostException) {
        Result.Error(UnknownHostException("Unable to connect with server. Check your connection."))
    }
}