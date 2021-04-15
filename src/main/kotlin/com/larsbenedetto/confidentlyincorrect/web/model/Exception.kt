package com.larsbenedetto.confidentlyincorrect.web.model

import com.larsbenedetto.confidentlyincorrect.domain.Identity

class ValidationException(override val message: String?) : RuntimeException(message)

class EntityLookupFailedException(
    entityName: String?,
    entityId: Identity<*>
) : RuntimeException("Could not find entity $entityName for id ${entityId.value}")