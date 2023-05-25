package com.technosudo.httptransferer.repository

import com.technosudo.httptransferer.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Int> {}