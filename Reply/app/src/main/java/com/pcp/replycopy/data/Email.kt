package com.pcp.replycopy.data

/*
    Author: Joey
    Start time: 22/10/20
    Point:
      1. 此 data class包含了其他的data class, 如Account, EmailAttachment, MailboxType
 */
data class Email(
    val id: Long,
    val sender: Account,  //重要: Account 是另一個data class
    val recipients: List<Account> = emptyList(),
    val subject: String,
    val body: String,
    val attachments: List<EmailAttachment> = emptyList(),
    var isImportant: Boolean = false,
    var isStarred: Boolean = false,
    var mailbox: MailboxType = MailboxType.INBOX,
    val createdAt: String,
    val threads: List<Email> = emptyList()
)
