package com.dicoding.justview.core.utils

import com.dicoding.justview.core.data.source.local.entity.ViewEntity
import com.dicoding.justview.core.data.source.remote.response.ViewResponse
import com.dicoding.justview.core.domain.model.View

object DataMapper {

    fun mapResponsesToEntities(input: List<ViewResponse>) = input.map {
        ViewEntity(
            viewId = it.sourceLink,
            name = it.name,
            image = it.image,
            width = it.width,
            height = it.height,
            source = it.source,
            sourceLink = it.sourceLink,
            lat = it.lat,
            lng = it.lng,
        )
    }

    fun mapEntitiesToDomain(input: List<ViewEntity>) = input.map {
        View(
            viewId = it.viewId,
            name = it.name,
            image = it.image,
            width = it.width,
            height = it.height,
            source = it.source,
            sourceLink = it.sourceLink,
            lat = it.lat,
            lng = it.lng,
            isFavorite = it.isFavorite
        )
    }

    fun mapDomainToEntity(input: View) = ViewEntity(
        viewId = input.viewId,
        name = input.name,
        image = input.image,
        width = input.width,
        height = input.height,
        source = input.source,
        sourceLink = input.sourceLink,
        lat = input.lat,
        lng = input.lng,
        isFavorite = input.isFavorite
    )


    fun mapEntityToDomain(input: ViewEntity?): View? {
        if (input == null) return null

        return View(
            viewId = input.viewId,
            name = input.name,
            image = input.image,
            width = input.width,
            height = input.height,
            source = input.source,
            sourceLink = input.sourceLink,
            lat = input.lat,
            lng = input.lng,
            isFavorite = input.isFavorite
        )
    }
}