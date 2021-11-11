package com.nt.intercorp_login.base

import com.nt.intercorp_login.base.RepositoryFactory.getRetrofit

open class BaseRepository<T>(
    service: Class<T>,
) {
    var service: T = getRetrofit()
        .create(service)
}