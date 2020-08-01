object KotlinDeps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

object CoresDeps {
    const val di = ":core:core-di"
    const val ui = ":core:core-ui"
    const val tools = ":core:core-tools"
    const val domain = ":core:core-domain"
    const val data = ":core:core-data"
    const val db = ":core:core-db"
    const val places = ":core:core-places"
    const val network = ":core:core-network"
    const val test = ":core:core-test"
}

object ContainersDeps {
    const val main = ":container:container-main"
}

object LibrariesDeps {
    const val oauth2token = ":libraries:oauth2token"
    const val inputMask = "com.redmadrobot:input-mask-android:${Versions.inputMask}"
    const val commonDelegates = ":libraries:common-delegates"
    const val singletonHolder = ":libraries:singleton-holder"
    const val awesomeValidation =
        "com.github.thyrlian:AwesomeValidation:${Versions.awesomeValidation}"
    const val mviCoreDiff = "com.github.badoo.mvicore:mvicore-diff:${Versions.mviCoreDiff}"
}

object FlowsDeps {
    const val connectCity = ":flows:flow-connect-city"
    const val main = ":flows:flow-main"
    const val auth = ":flows:flow-auth"
    const val settings = ":flows:flow-settings"
    const val more = ":flows:flow-more"
    const val history = ":flows:flow-history"
    const val myAddresses = ":flows:flow-my-addresses"
    const val payment = ":flows:flow-payment"
    const val profile = ":flows:flow-profile"
}

object FeatureDeps {
    const val address = ":feature:address"
    const val connectCity = ":feature:connect-city"
    const val aboutApp = ":feature:about-app"
    const val splash = ":feature:entry:splash"
    const val services = ":feature:services"
    const val history = ":feature:history"
    const val more = ":feature:more"
    const val profile = ":feature:profile"
    const val mapPhotoSalons = ":feature:photosalons-map"
    const val faq = ":feature:faq"
    const val settings = ":feature:settings"
    const val signIn = ":feature:entry:sign-in"
    const val signInVerification = ":feature:entry:sign-in-verification"
    const val selectTheme = ":feature:select-theme-dialog"
    const val noNetworkConnection = ":feature:no-network-connection-dialog"
    const val selectPlaceCity = ":feature:select-place-city"
    const val myAddresses = ":feature:my-addresses"
    const val paymentType = ":feature:payment-type"
    const val logout = ":feature:logout-dialog"
    const val selectImageSource = ":feature:select-image-source"
    const val cropImage = ":feature:crop-image"

}

object AndroidDeps {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
    const val design = "com.google.android.material:material:${Versions.materialDesign}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
}

object MapDeps {
    const val places = "com.google.android.libraries.places:places:${Versions.places}"
}

object NavigationDeps {
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object LifecycleDeps {
    const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
}

object DaggerDeps {
    const val runtime = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val inject = "javax.inject:javax.inject:${Versions.inject}"
    const val assistedInject =
        "com.github.davidliu.AssistedInject:assisted-inject-annotations-dagger2:${Versions.assistedInject}"
    const val assistedInjectCompiler =
        "com.github.davidliu.AssistedInject:assisted-inject-processor-dagger2:${Versions.assistedInject}"
}

object RxDeps {
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
    const val rxRoom = "androidx.room:room-rxjava2:${Versions.room}"
    const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val rxErrorAdapter = "com.olegsheliakin:rx-error-adapter:${Versions.rxErrorAdapter}"
    const val rxRelay2 = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelay2}"
}

object RoomDeps {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
}

object NetworkDeps {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val loggingInterceptor =
        "com.github.ihsanbal:LoggingInterceptor:${Versions.loggingInterceptor}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.converterMoshi}"
}

object GlideDeps {
    const val runtime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object KtxDeps {
    const val core = "androidx.core:core-ktx:${Versions.ktx}"
}

object MoshiDeps {
    const val runtime = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val compiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

}

object UtilsDeps {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenabp}"
    const val keyboardVisibilityEvent =
        "net.yslibrary.keyboardvisibilityevent:keyboardvisibilityevent:${Versions.keyboardVisibilityEvent}"
    val adapterDelegateKotlinDsl =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.adapterDelegateKotlinDsl}"
}

object WidgetDeps {
    const val progressButton =
        "com.github.razir.progressbutton:progressbutton:${Versions.progressButton}"
    const val toasty = "com.github.GrenderG:Toasty:${Versions.toasty}"
    const val expandIcon = "com.github.zagum:Android-ExpandIcon:${Versions.expandIcon}"
    const val expandableLayout =
        "net.cachapa.expandablelayout:expandablelayout:${Versions.expandableLayout}"
    const val persistentSearchView =
        "com.paulrybitskyi.persistentsearchview:persistentsearchview:${Versions.persistentSearchView}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.junit}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val testCore = "junit:junit:${Versions.testCore}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val aac = "androidx.arch.core:core-testing:${Versions.aac}"
}

object Payments {
    const val tinkoffAcquiringUISdk = "ru.tinkoff.acquiring:ui:${Versions.tinkoffAcquiringSdk}"
    const val tinkoffAcquiringCoreSdk = "ru.tinkoff.acquiring:core:${Versions.tinkoffAcquiringSdk}"
}

object Insetter {
    const val lib = "dev.chrisbanes:insetter:${Versions.insetter}"
    const val ktx = "dev.chrisbanes:insetter-ktx:${Versions.insetter}"
    const val widgets = "dev.chrisbanes:insetter-widgets:${Versions.insetter}"
}

object Permissions {
    const val runtime =
        "org.permissionsdispatcher:permissionsdispatcher:${Versions.permissionsDispatcher}"
    const val compiler =
        "org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permissionsDispatcher}"
}
