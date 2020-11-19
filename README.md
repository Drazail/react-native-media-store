# react-native-media-store

## this package only supports Android

## Instalation

`$ npm i react-native-media-store`

## Imports

```javascript
import { MusicFiles, Constants, CoverImage } from "react-native-media-store";
```

## Constants

```javaScript
{
  SortBy: {
    Artist: 'ARTIST',
    Album: 'ALBUM',
    Title: 'TITLE',
  },
  SortOrder: {
    Ascending: 'ASC',
    Descending: 'DESC',
  },
}

```

### example

```javascript

import {Constants} from 'react-native-media-store';

const sortBy = Constants.SortBy.Title,
const sortOrder = Constants.SortOrder.Ascending,

```

## Methods

```javascript
import { MusicFiles } from "react-native-media-store";
```

check index.d.ts

### Components

If you want to show only a couple of covers in your page using this component instead of `cover: true` should yield better performance.

:heavy_exclamation_mark: this component runs on the Main thread. using it in larg lists WILL cause performance issues.

Extends Image.

```javascript
<CoverImage src={"pathToSong"} width={120} height={120} />
```

