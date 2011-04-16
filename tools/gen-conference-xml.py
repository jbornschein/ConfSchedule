#!/usr/bin/env python

import sys
import random

days = ["2011-05-23", "2011-05-24", "2011-06-25"]
authors = ["Jane Mustermann", "Foo Bar", "John Bill", "Boring Jack"]

title1 = ["Introduction to ", "Progress in ", "Workshop on "]
title2 = ["persistant computing ", "funny math ", "wrong conclusions "]

abstract = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."

tracks = 3
first_hour = 9
last_hour = 17

def add_duration(hour, minutes, duration):
    minutes += duration
    hour += minutes // 60
    minutes %= 60
    return hour, minutes


print "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
print "<conference>"
print "  <name>1st International Testing Conference</name>"    
print "  <url>http://www.capsec.org/TestConf</url>"
for d in xrange(len(days)):
    print "  <day>"
    print "    <date>%s</date>" % days[d]
    print "    <name>Day %d</name>" % (d+1)
    for t in xrange(tracks):
        print "    <track>"
        print "      <room>Room %d</room>" % t
        hour = first_hour
        minute = 0
        while hour < last_hour:
            title = random.choice(title1) + random.choice(title2)
            author = random.choice(authors)
            duration = random.choice( [45, 60, 90] )
            end_hour, end_minute = add_duration(hour, minute, duration)
            print "      <event>"
            print "        <title>%s</title>" %  title
            print "        <author>%s</author>" % author
            print "        <start>%d:%02d</start>" % (hour, minute)
            print "        <end>%d:%02d</end>" % (end_hour, end_minute)
            print "        <abstract>%s</abstract>" % abstract
            print "      </event>"
            duration = random.choice( [0, 15, 30] )
            hour, minute = add_duration(end_hour, end_minute, duration)
        print "    </track>"
    print "  </day>"

print "</conference>"


