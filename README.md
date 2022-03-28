# Library for Supabase Storage
## Install
### With BOM
```groovy  
dependencies {  
 implementation platform("io.github.ferhatwi:supabase-kt-bom:0.1.0")
 implementation "io.github.ferhatwi:supabase-storage-kt"
}  
```  
### Without BOM
#### NOTICE: BOM is strongly recommended to prevent conflicts.
```groovy 
dependencies {  
 implementation "io.github.ferhatwi:supabase-storage-kt:0.1.0"
}  
```  
## How to use?
### REMINDER
Supabase should be initialized before using this library. Instructions are [here](https://github.com/ferhatwi/supabase-kt).
#
```kotlin
val storage = Supabase.storage()  
```  
#### Get Bucket
```kotlin
storage
    .bucket("BUCKET_NAME")
    .get()
    .catch {

    }.collect {

    }
```
#### Make Bucket Public
```kotlin
storage
    .bucket("BUCKET_NAME")
    .makePublic()
    .catch {

    }.collect {

    }
```
#### Make Bucket Private
```kotlin
storage
    .bucket("BUCKET_NAME")
    .makePrivate()
    .catch {

    }.collect {

    }
```
#### Create Bucket
```kotlin
storage
    .bucket("BUCKET_NAME")
    .create(PUBLIC_OR_PRIVATE)
    .catch {

    }.collect {

    }
```
#### Empty Bucket
```kotlin
storage
    .bucket("BUCKET_NAME")
    .empty()
    .catch {

    }.collect {

    }
```
#### Delete Bucket
```kotlin
storage
    .bucket("BUCKET_NAME")
    .delete()
    .catch {

    }.collect {

    }
```
#### List Files
###### Optional
- Folder
- Sort
- Offset
- Limit
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .sort(SORTABLE, ASCENDING_OR_DESCENDING)
    .offset(VALUE)
    .limit(VALUE)
    .listFiles()
    .catch {

    }.collect {

    }
```
#### List Folders
###### Optional
- Folder
- Sort
- Offset
- Limit
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .sort(SORTABLE, ASCENDING_OR_DESCENDING)
    .offset(VALUE)
    .limit(VALUE)
    .listFolders()
    .catch {

    }.collect {

    }
```
#### Upload File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .upload(DATA_OR_FILE, "CONTENT_TYPE")
    .catch {

    }.collect {

    }
```
#### Upsert File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .upsert(DATA_OR_FILE, "CONTENT_TYPE")
    .catch {

    }.collect {

    }
```
#### Get File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .get()
    .catch {

    }.collect {

    }
```
#### Save File To Local
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .saveTo("DESTINATION")
    .catch {

    }.collect {

    }
```
#### Update File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .update(DATA_OR_FILE, "CONTENT_TYPE")
    .catch {

    }.collect {

    }
```
#### Move File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .moveFromHere(
        to = {
            folder("FOLDER_NAME").file("FILE.EXT")
        }
    )
    .catch {

    }.collect {

    }
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .moveToHere(
        from = {
            folder("FOLDER_NAME").file("FILE.EXT")
        }
    )
    .catch {

    }.collect {

    }
```
#### Copy File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .copyFromHere(
        to = {
            folder("FOLDER_NAME").file("FILE.EXT")
        }
    )
    .catch {

    }.collect {

    }
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .copyToHere(
        from = {
            folder("FOLDER_NAME").file("FILE.EXT")
        }
    )
    .catch {

    }.collect {

    }      
```
#### Create Signed URL for File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .createSignedURL(EXPIRES_IN)
    .catch {

    }.collect {

    }    
```
#### Remove File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .remove()
    .catch {

    }.collect {

    }    
```
#### Get Public URL of File
###### Optional
- Folder
```kotlin
storage
    .bucket("BUCKET_NAME")
    .folder("FOLDER_NAME")
    .file("FILE.EXT")
    .getPublicURL()
```
## Improvements and Bugs
Feel free to improve, upgrade, fix or report bugs!