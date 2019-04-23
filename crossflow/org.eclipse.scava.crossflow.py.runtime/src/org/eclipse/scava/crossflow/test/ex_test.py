'''
Created on 28 Mar 2019

@author: stevet
'''
import org.eclipse.scava.crossflow.utils.basic_xstream as bxstream
import traceback
import sys

def bar():
    ''.honk()
    
def foo():
    bar()
    
if __name__ == '__main__':
    
    try:
        foo()
    except Exception as ex:
        exc_type, exc_value, exc_traceback = sys.exc_info()
        stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
        print(bxstream.pyExceptionToJavaXML(ex, stack))