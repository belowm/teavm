#include <string.h>
#include <stdint.h>
#include <uchar.h>

#define HEADER_BITS_RESERVED 3

typedef struct {
    uintptr_t header;
    uint32_t hashCode;
} JavaObject;

typedef struct {
    JavaObject parent;
    int32_t length;
} JavaArray;

typedef struct {
    JavaObject parent;
} JavaClass;

typedef struct {
    JavaObject parent;
    JavaArray *characters;
    int32_t hashCode;
} JavaString;

#define PACK_CLASS(cls) (((uintptr_t) cls) >> HEADER_BITS_RESERVED)
#define UNPACK_CLASS(cls) (((JavaClass *) ((uintptr_t) cls << HEADER_BITS_RESERVED)))
#define CLASS_OF(obj) (((JavaClass *) ((uintptr_t) obj->header << HEADER_BITS_RESERVED)))
#define AS(ptr, type) ((type *) ptr)

#define VTABLE(obj, type) ((type *) UNPACK_CLASS(obj))
#define METHOD(obj, type, method) UNPACK_CLASS(obj)->method
#define FIELD(ptr, type, name) AS(ptr, type)->name;

#define TO_BYTE(i) (((i << 24) >> 24))
#define TO_SHORT(i) (((i << 16) >> 16))
#define TO_CHAR(i) ((char16_t) i)

#define COMPARE(a, b) (a > b ? 1 : a < b : -1 : 0)

#define ARRAY_LENGTH(array) (((JavaArray *) array)->length)
#define ARRAY_DATA(array, type) ((type *) (((JavaArray *) array) + 1))

#define CHECKCAST(obj, cls) (cls(obj) ? obj : throwClassCastException())
static JavaObject* throwClassCastException();




static JavaString *stringPool;