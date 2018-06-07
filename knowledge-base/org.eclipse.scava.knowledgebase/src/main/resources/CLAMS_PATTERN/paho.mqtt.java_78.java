{
    File dir;
    final String MESSAGE_BACKUP_FILE_EXTENSION;
    File[] files = dir.listFiles(new PersistanceFileFilter(MESSAGE_BACKUP_FILE_EXTENSION));

    if (files == null) {
        throw new MqttPersistenceException();
    }
}