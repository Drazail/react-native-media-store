
import { ImageProps } from "react-native";

// constsnts
export namespace Constants {
    export namespace SortBy {
        export const Artist: string;
        export const Album: String;
        export const Title: String;
        export const DATE_ADDED: String;
        export const DATE_MODIFIED: String;
    }
    export namespace SortOrder {
        export const Ascending: String;
        export const Descending: String;
    }
}

// components
interface ICoverImageProps extends ImageProps {
    src: string;
}
export const CoverImage: (props: ICoverImageProps) => React.FunctionComponent<ICoverImageProps>;

export namespace MusicFiles {

    export function extractFilesInDirectory(options: {
        path?: string | string[],
        cover?: boolean,
        coverFolder?: string,
        coverSizeLimit?: number,
        coverWidth?: number,
        minFileSize?: number,
        maxFileSize?: number,
        extensionFilter?: string,
        sorted?: boolean,
        batchSize?: number,
        batchNumber?: number,
        metaData?: boolean,

    }): Promise<{
        id: number,
        path: string,
        cover?: string,
        duration: number,
        album: string,
        artist: string,
        title: string
    }>;

    export function extractFileMetaData(options: {
        path?: string,
        cover?: boolean,
        coverFolder?: string,
        coverSizeLimit?: number,
        coverWidth?: number,
    }): Promise<{
        id: number,
        path: string,
        cover?: string,
        duration: number,
        album: string,
        artist: string,
        title: string
    }[]
    >;

    export function getArtists(options: {
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            artist: string,
            numberOfAlbums: number,
            numberOfSongs: number,
        }[],
        "length": number
    }>;

    export function getAlbums(options: {
        artist?: string,
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: string,
            album: string,
            artist: string,
            numberOfSongs: number,
            firstYear: number,
            lastYear: number,
        }[],
        "length": number
    }>;

    export function getSongs(options: {
        title?: string,
        artist?: string,
        album?: string,
        allTexts?: string,
        notTitle?: string,
        notArtist?: string,
        notAlbum?: string,
        batchSize?: number,
        batchNumber?: number,
        minDuration?: number,
        maxDuration?: number,
        minSize?: number,
        maxSize?: number,
        cover?: boolean,
        coverFolder?: boolean,
        coverSizeLimit?: number,
        coverWidth?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: string,
            title: string,
            album: string,
            artist: string,
            duration: number,
            size: number,
            path: string,
            lastModified: number,
            dateAdded: number,
            track: number,
            year: number,
            cover: string,
        }[],
        "length": number
    }>;

}




