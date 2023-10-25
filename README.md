# BeerApp

## Creare un UseCase
Per creare una classe UseCase bisogna implementare l'interfaccia UseCase. Nel costruttore aggiungere il repository desiderato e nella funzione useCase() richiamare il repo e ritornare il risultato della query

## Come usare un UseCase all'interno di un fragment
Creare l'oggetto UseCase desiderato e chiamare la funzione useCase(), aggiungere un listener tramite il metodo addOnSuccessListener, esempio:
  val exampleUseCase = ExampleUseCase()
  exampleUseCase.useCase()
    .addOnSuccessListener{ documents ->
      documents.forEach{ doc ->
        val obj = doc.toObject(exampleObject::class)
        // fai cose che devi fare con l'oggetto
        }
      }

## imageRepository
Per le immagini non serve creare un useCase, basta richiamare il repository delle immagini e caricarle nell'imageView, esempio:
  val imageRepository = ImageRepository()
  imageRepository.loadImage(uid_immagine_da_usere)
    .addOnSuccessListener{ imageByte->
      val bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size);
      imageView.setImageBitmap(bitmap)
    }

