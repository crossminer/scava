'''
Created on 11 Mar 2019

This is a very crude xml<->obj converter which mirrors certain parts of the functionality of XStream.
It does fields of basic built-in types and enums and that's it.
Converts camel case to snake case and vice versa.
@author: stevet
'''
import xmltodict
import re

DEBUG = False
CAMEL2SNAKE = False

util_types = ['CloneUtils', 'ControlSignals', 'ControlSignal', 'CSVParser', 'CSVWriter', 'QueueType', 'QueueInfo',
'Stream', 'StreamMetadata', 'TaskStatuses', 'TaskStatus']


def camel2Snake(name):
    words = re.findall(r'[A-Z]?[a-z]+|[A-Z]{2,}(?=[A-Z][a-z]|\d|\W|$)|\d+', name)
    return '_'.join(map(str.lower, words))


def snake2Camel(snake_str):
    first, *others = snake_str.split('_')
    return ''.join([first.lower(), *map(str.title, others)])


def debug(debug_str):
    if(DEBUG):
        print(debug_str)

        
def getClazz(clazz):
    pkgs = clazz.split('.')
    module = ".".join(pkgs[:-1])
    m = __import__(module)
    for comp in pkgs[1:]:
        m = getattr(m, comp)            
    return m


def fullName(o):
    module = o.__class__.__module__
    if module is None or module == str.__class__.__module__:
        return o.__class__.__name__  
    else:
        return module + '.' + o.__class__.__name__


def pyClass2JavaClass(pyname):
    fn = fullName(pyname)
    shortName = fn[fn.rindex('.') + 1:len(fn)]
    numShortNames = len(list(filter(lambda x: x == shortName, fn.split('.'))))
    if numShortNames == 2:
        java_package = fn[0:fn.index('.' + shortName + '.' + shortName) + 1]
    else:
        java_package = fn[0:fn.index('.' + shortName) + 1]
    if shortName in util_types:
        return java_package + 'utils.' + shortName
    else:
        return java_package + shortName  
  

def javaClassToPyClass(javaName):
    shortName = javaName[javaName.rindex('.') + 1:len(javaName)]
    java_package = javaName[0:javaName.index('.' + shortName)]
    rPos = java_package.find('runtime')
    if rPos > 0:
        if java_package.find('runtime.utils') > -1:
            java_package = java_package[0:java_package.index('utils') - 1]
        return java_package + '.' + shortName
    else:
        return java_package + '.runtime.' + shortName + '.' + shortName
        
    
def propertiesOnly(member, in_obj):
    if member.startswith('__'):
        return False
    if str(getattr(in_obj, member)).startswith('<bound method'):
        return False
    if str(getattr(in_obj, member)).startswith('<function'):
        return False
    return True

    
def getSimpleClassName(in_obj):
    in_obj_type = str(type(in_obj))
    return in_obj_type[in_obj_type.rindex('.') + 1:len(in_obj_type) - 2]


def bxToString(in_obj, package=''):
    members = list(filter(lambda x: propertiesOnly(x, in_obj), dir(in_obj)))
    x_dict = {}
    m_dict = {}
    for m in members:
        m_val = str(getattr(in_obj, m))
        debug(m + ' = ' + m_val)
        if CAMEL2SNAKE:
            m_dict[snake2Camel(m)] = m_val
        else:
            m_dict[m] = m_val
    in_obj_type = str(type(in_obj))
    full_name = pyClass2JavaClass(in_obj)
    op = getattr(in_obj, "get_serialisation_name", None)
    if callable(op):
        full_name = in_obj.get_serialisation_name()
    if package != None and len(package) > 0:
        full_name = package + in_obj_type[in_obj_type.rindex('.') + 1:len(in_obj_type) - 2]
    x_dict[full_name] = m_dict
    ret = xmltodict.unparse(x_dict, pretty=True)
    debug('Encoded from python:')
    debug(ret)
    return ret


def bxToObject(xml_str):
    x_dict = xmltodict.parse(xml_str)
    # print(json.dumps(x_dict, indent=4))
    java_class_name = list(x_dict.keys())[0]
    py_clazz_name = javaClassToPyClass(java_class_name)
    debug("class name = " + py_clazz_name)
    clazz_type = getClazz(py_clazz_name)
    debug(clazz_type)
    clazz_instance = clazz_type()
    debug(clazz_instance)
    vals_dict = x_dict[java_class_name]
    for p in list(vals_dict.keys()):
        propName = p
        if CAMEL2SNAKE:
            propName = camel2Snake(p)
        test_val = getattr(clazz_instance, propName)
        new_val = vals_dict[p] 
        v_type = type(test_val)
        if v_type is int:
            new_val = int(new_val)
        elif v_type is float:
            new_val = float(new_val)
        elif v_type is bool:
            if new_val in ['True', 'true', 'TRUE']:
                new_val = True
            else:
                new_val = False
        elif v_type is str:
            new_val = str(new_val)
        else:
            if(str(v_type).startswith('<enum')):
                new_val = v_type.enum_from_name(new_val)
            else:
                print('Unknown type: ' + str(v_type))
        setattr(clazz_instance, propName, new_val)
        debug("type=" + str(type(test_val)))
    return clazz_instance


JAVA_EXCEPTION_TEMPLATE = '''
<java.lang.Exception>
  <detailMessage>Detail here</detailMessage>
  <stackTrace>
    <trace>LINE1</trace>
    <trace>LINE2</trace>
  </stackTrace>
  <suppressedExceptions class="java.util.Collections$UnmodifiableRandomAccessList" resolves-to="java.util.Collections$UnmodifiableList">
    <c class="list"/>
    <list reference="../c"/>
  </suppressedExceptions>
</java.lang.Exception>'''


def pyExceptionToJavaXML(ex, st=None):
    x_dict = xmltodict.parse(JAVA_EXCEPTION_TEMPLATE)
    x_dict['java.lang.Exception']['detailMessage'] = '!PYTHON! ' + str(ex)
    x_dict['java.lang.Exception']['stackTrace']['trace'] = st
    return xmltodict.unparse(x_dict, pretty=True)
