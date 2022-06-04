package com.alisayar.kitapligimuygulamas_proje2.network

data class BookModel(
    var items: List<Item>? = null,
    var kind: String? = null,
    var totalItems: Int? = null
)

data class Item(
    var accessInfo: AccessInfo? = null,
    var etag: String? = null,
    var id: String? = null,
    var kind: String? = null,
    var saleInfo: SaleInfo? = null,
    var searchInfo: SearchInfo? = null,
    var selfLink: String? = null,
    var volumeInfo: VolumeInfo? = null
)

data class AccessInfo(
    var accessViewStatus: String? = null,
    var country: String? = null,
    var embeddable: Boolean? = null,
    var epub: Epub? = null,
    var pdf: Pdf? = null,
    var publicDomain: Boolean? = null,
    var quoteSharingAllowed: Boolean? = null,
    var textToSpeechPermission: String? = null,
    var viewability: String? = null,
    var webReaderLink: String? = null
)

data class SaleInfo(
    var buyLink: String? = null,
    var country: String? = null,
    var isEbook: Boolean? = null,
    var listPrice: ListPrice? = null,
    var offers: List<Offer>? = null,
    var retailPrice: RetailPriceX? = null,
    var saleability: String? = null
)

data class SearchInfo(
    var textSnippet: String? = null
)

data class VolumeInfo(
    var allowAnonLogging: Boolean? = null,
    var authors: List<String>? = null,
    var averageRating: Double? = null,
    var canonicalVolumeLink: String? = null,
    var categories: List<String>? = null,
    var contentVersion: String? = null,
    var description: String? = null,
    var imageLinks: ImageLinks? = null,
    var industryIdentifiers: List<IndustryIdentifier>? = null,
    var infoLink: String? = null,
    var language: String? = null,
    var maturityRating: String? = null,
    var pageCount: Int? = null,
    var panelizationSummary: PanelizationSummary? = null,
    var previewLink: String? = null,
    var printType: String? = null,
    var publishedDate: String? = null,
    var publisher: String? = null,
    var ratingsCount: Double? = null,
    var readingModes: ReadingModes? = null,
    var subtitle: String? = null,
    var title: String? = null
)

data class Epub(
    var acsTokenLink: String? = null,
    var downloadLink: String? = null,
    var isAvailable: Boolean? = null
)

data class Pdf(
    var acsTokenLink: String? = null,
    var isAvailable: Boolean? = null
)

data class ListPrice(
    var amount: Double? = null,
    var currencyCode: String? = null
)

data class Offer(
    var finskyOfferType: Int? = null,
    var listPrice: ListPriceX? = null,
    var retailPrice: RetailPrice? = null
)

data class RetailPriceX(
    var amount: Double? = null,
    var currencyCode: String? = null
)

data class ListPriceX(
    var amountInMicros: Double? = null,
    var currencyCode: String? = null
)

data class RetailPrice(
    var amountInMicros: Double? = null,
    var currencyCode: String? = null
)

data class ImageLinks(
    var smallThumbnail: String? = null,
    var thumbnail: String? = null
)

data class IndustryIdentifier(
    var identifier: String? = null,
    var type: String? = null
)

data class PanelizationSummary(
    var containsEpubBubbles: Boolean? = null,
    var containsImageBubbles: Boolean? = null
)

data class ReadingModes(
    var image: Boolean? = null,
    var text: Boolean? = null
)
