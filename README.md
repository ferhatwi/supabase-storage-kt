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
        .get(
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Make Bucket Public
```kotlin
storage
        .bucket("BUCKET_NAME")
        .makePublic(
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Make Bucket Private
```kotlin
storage
        .bucket("BUCKET_NAME")
        .makePrivate(
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Create Bucket
```kotlin
storage
        .bucket("BUCKET_NAME")
        .create(
            PUBLIC_OR_PRIVATE,
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Empty Bucket
```kotlin
storage
        .bucket("BUCKET_NAME")
        .empty(
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Delete Bucket
```kotlin
storage
        .bucket("BUCKET_NAME")
        .delete(
            PUBLIC_OR_PRIVATE,
            onFailure = {

            }, onSuccess = {

            }
        )
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
        .listFiles(
            onFailure = {
            }, onSuccess = {

            }
        )
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
        .listFolders(
            onFailure = {
            }, onSuccess = {

            }
        )
```
#### Upload File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .upload(
            DATA_OR_FILE,
            "CONTENT_TYPE",
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Upsert File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .upsert(
            DATA_OR_FILE,
            "CONTENT_TYPE",
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Get File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .get(
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Save File To Local
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .saveTo(
            "DESTINATION",
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Update File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .update(
            DATA_OR_FILE,
            "CONTENT_TYPE",
            onFailure = {

            }, onSuccess = {

            }
        )
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
            }, onFailure = {

            }, onSuccess = {

            }
        )
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .moveToHere(
            from = {
                folder("FOLDER_NAME").file("FILE.EXT")
            }, onFailure = {

            }, onSuccess = {

            }
        )        
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
            }, onFailure = {

            }, onSuccess = {

            }
        )
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .copyToHere(
            from = {
                folder("FOLDER_NAME").file("FILE.EXT")
            }, onFailure = {

            }, onSuccess = {

            }
        )        
```
#### Create Signed URL for File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .createSignedURL(
            EXPIRES_IN,
            onFailure = {

            }, onSuccess = {

            }
        )
```
#### Remove File
###### Optional
- Folder
```kotlin
storage
        .bucket("BUCKET_NAME")
        .folder("FOLDER_NAME")
        .file("FILE.EXT")
        .remove(
            onFailure = {

            }, onSuccess = {

            }
        )
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