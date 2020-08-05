package com.mercuriy94.gameoflife.core.navigation

/**
 * Команды с поеддержкой переходов на активити (Например экраны системные настройки)
 *
 * @author Nikita Marsyukov
 * */
sealed class Command

object BackCommand : Command()
class NavigateCommand(val navigationCommand: NavigationCommand) : Command()
class StartActivityCommand(val intentProducer: IntentProducer) : Command()
class StartServiceCommand(val intentProducer: IntentProducer) : Command()
