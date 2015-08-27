JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
run:
	$(java) java DrivingRangeApp 3 20 4

