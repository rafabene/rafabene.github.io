import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat

/**
 * Parses the xml file produced by the template from jroller and generates
 * wxr xml file for importing in disqus
 */

sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
sdf2.setTimeZone(TimeZone.getTimeZone('GMT'))

def xml = new XmlSlurper()
def posts = xml.parse(new File(args[0]))

def writer = new StringWriter()
def builder = new MarkupBuilder(writer)

String toGMT(date)
{
  date = "${date[0..-6]}${date[-5..-1].replace(':', '')}"
  date = sdf.parse(date)
  date = sdf2.format(date)

  return date
}

int commentId = 100

builder.channel {
  posts.post.each { post ->

    def date = toGMT(post.'@date'.toString())
    def postLink = post.'@permalink'.toString()
    def postId = postLink - 'http://www.pongasoft.com/blog/yan'
    postId = postId.replace('_', '-')

    def postComments = post.comments?.comment?.list()

    if(postComments)
    {
      item {
        // title of article
        title(post.'@title'.toString())

        // absolute URI to article
        link(postLink)

        // thread body; use cdata; html allowed (though will be formatted to DISQUS specs)
        'content:encoded'('...')
        // value used within disqus_identifier; usually internal identifier of article
        'dsq:thread_identifier'(postId)

        // creation date of thread (article), in GMT
        'wp:post_date_gmt'(date)

        // open/closed values are acceptable
        'wp:comment_status'('open')

        postComments.each { comment ->
          'wp:comment' {

            // internal id of comment
            'wp:comment_id'(commentId++)

          // author display name
          'wp:comment_author'(comment.'@name'.toString())

          // author email address
          'wp:comment_author_email'(comment.'@email'.toString())

          // author url, optional
          'wp:comment_author_url'(comment.'@url'.toString())

          // author ip address
          'wp:comment_author_IP'(comment.'@remoteHost'.toString())

          // comment datetime, in GMT
          'wp:comment_date_gmt'(toGMT(comment.'@date'.toString()))

          // comment body; use cdata; html allowed (though will be formatted to DISQUS specs)
          'wp:comment_content'(comment.content.text())

          // is this comment approved? 0/1
          'wp:comment_approved'(1)
          }
        }
      }
    }
    else
    {
      // println "no comments for ${postLink}"
    }
  }
}

println """<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0"
  xmlns:content="http://purl.org/rss/1.0/modules/content/"
  xmlns:dsq="http://www.disqus.com/"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns:wp="http://wordpress.org/export/1.0/"
>
${writer.toString()}
</rss>
"""
