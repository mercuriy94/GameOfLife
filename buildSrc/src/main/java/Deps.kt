object KotlinDeps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

object CommonDeps {
    const val di = ":common:core-di"
    const val ui = ":common:core-ui"
    const val mvi = ":common:core-mvi"
    const val navigation = ":common:core-navigation"
    const val flow = ":common:core-flow"
    const val tools = ":core:core-tools"
    const val domain = ":common:core-domain"
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
    const val mviCoreDiff = "com.github.badoo.mvicore:mvicore-diff:${Versions.mviCoreDiff}"
}

object FlowsDeps {
    const val main = ":flow:flow-main"
}

object FeatureDeps {
    const val life = ":feature:feature-life"
}

object AndroidDeps {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidSupport}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val design = "com.google.android.material:material:${Versions.materialDesign}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
}

object MapDeps {
    const val places = "com.google.android.libraries.places:places:${Versions.places}"
}

object NavigationDeps {
    const val runtime = "androidx.navigation:navigation-runtime:${Versions.navigation}"
    const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object LifecycleDeps {
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
}

object DI {
    const val daggerRuntime = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
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
    const val lifeField = ":widget:widget-life"
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

object DebugDeps {
    const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
}
