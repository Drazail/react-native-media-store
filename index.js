import { NativeModules, requireNativeComponent } from 'react-native';


export const { MusicFiles } = NativeModules;

export const Constants = {
  SortBy: {
    Artist: 'ARTIST',
    Album: 'ALBUM',
    Title: 'TITLE',
    DATE_MODIFIED:'DATE_MODIFIED',
    DATE_ADDED: 'DATE_ADDED',
  },
  SortOrder: {
    Ascending: 'ASC',
    Descending: 'DESC',
  },
};

export const CoverImage = requireNativeComponent('RCTCoverImageView');
