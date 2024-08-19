package com.example.api.mart.service

import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class TransactionalLockHelper(private val distributedLockHelper: DistributedLockHelper) {
    fun lockAndRegisterUnlock(key: String): Boolean {
        if (distributedLockHelper.lock(key)) {
            TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                override fun afterCompletion(status: Int) {
                    distributedLockHelper.unlock(key)
                }
            })
            return true
        }
        return false
    }
}