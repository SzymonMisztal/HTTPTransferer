package com.technosudo.httptransferer.service

import com.technosudo.httptransferer.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService @Autowired constructor(
    val accountRepository: AccountRepository
) {



}