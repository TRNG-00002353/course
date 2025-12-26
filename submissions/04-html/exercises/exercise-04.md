# Exercise: Build a Media Gallery Page

## Objective
Create a media gallery page showcasing images, audio, and video using HTML5 media elements with proper accessibility and responsive techniques.

## Requirements

Build an HTML page (`media-gallery.html`) that includes:

### 1. Page Structure
- Proper DOCTYPE and meta tags
- Page title: "Media Gallery"
- Navigation for different media sections (Images, Audio, Video)

### 2. Image Gallery Section

#### Basic Images
Create a gallery with at least 6 images using:

```html
<!-- Basic image -->
<img src="image.jpg" alt="Description" width="300" height="200" loading="lazy">

<!-- Figure with caption -->
<figure>
    <img src="image.jpg" alt="Description">
    <figcaption>Image caption goes here</figcaption>
</figure>

<!-- Responsive image with picture element -->
<picture>
    <source media="(min-width: 1200px)" srcset="large.jpg">
    <source media="(min-width: 768px)" srcset="medium.jpg">
    <img src="small.jpg" alt="Responsive image">
</picture>

<!-- Image with srcset for resolution switching -->
<img src="image-300.jpg"
     srcset="image-300.jpg 300w,
             image-600.jpg 600w,
             image-900.jpg 900w"
     sizes="(max-width: 600px) 300px,
            (max-width: 900px) 600px,
            900px"
     alt="Resolution switching image">
```

#### Required Image Features
- [ ] At least 2 images with `<figure>` and `<figcaption>`
- [ ] At least 1 `<picture>` element with multiple sources
- [ ] Use `loading="lazy"` for below-the-fold images
- [ ] All images must have descriptive `alt` text
- [ ] Include `width` and `height` attributes to prevent layout shift

### 3. Audio Section

Create an audio player section with at least 3 audio samples:

```html
<audio controls>
    <source src="audio.mp3" type="audio/mpeg">
    <source src="audio.ogg" type="audio/ogg">
    Your browser does not support the audio element.
</audio>
```

#### Audio Playlist Structure
```html
<article class="audio-item">
    <h3>Track Title</h3>
    <p>Artist Name | Album | Duration: 3:45</p>
    <audio controls preload="metadata">
        <source src="track.mp3" type="audio/mpeg">
        <source src="track.ogg" type="audio/ogg">
        <p>Your browser doesn't support audio.
           <a href="track.mp3">Download the track</a>
        </p>
    </audio>
</article>
```

#### Required Audio Features
- [ ] At least 3 audio tracks with controls
- [ ] Multiple source formats (mp3, ogg)
- [ ] Fallback text with download link
- [ ] Use `preload="metadata"` for efficient loading
- [ ] Track information (title, artist, duration)

### 4. Video Section

Create a video gallery with at least 2 videos:

```html
<video controls width="640" height="360" poster="thumbnail.jpg">
    <source src="video.mp4" type="video/mp4">
    <source src="video.webm" type="video/webm">
    <track kind="subtitles" src="subtitles-en.vtt" srclang="en" label="English">
    <track kind="subtitles" src="subtitles-es.vtt" srclang="es" label="Spanish">
    Your browser does not support the video element.
</video>
```

#### Video Card Structure
```html
<article class="video-item">
    <h3>Video Title</h3>
    <figure>
        <video controls width="100%" poster="poster.jpg" preload="metadata">
            <source src="video.mp4" type="video/mp4">
            <source src="video.webm" type="video/webm">

            <!-- Subtitles/Captions -->
            <track kind="captions" src="captions.vtt" srclang="en" label="English" default>

            <!-- Fallback content -->
            <p>Your browser doesn't support HTML5 video.</p>
            <a href="video.mp4">Download the video</a>
        </video>
        <figcaption>Video description goes here</figcaption>
    </figure>
    <p>Duration: 5:30 | Category: Tutorial</p>
</article>
```

#### Required Video Features
- [ ] At least 2 videos with controls
- [ ] Multiple source formats (mp4, webm)
- [ ] Poster images for video thumbnails
- [ ] At least one video with subtitle track (`<track>`)
- [ ] Fallback content for unsupported browsers
- [ ] Use `preload="metadata"` for efficient loading

### 5. Embedded Media Section

Include examples of embedded content:

```html
<!-- YouTube embed (use iframe) -->
<iframe width="560" height="315"
        src="https://www.youtube.com/embed/VIDEO_ID"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen>
</iframe>

<!-- Map embed -->
<iframe src="https://maps.google.com/..."
        width="600" height="450"
        style="border:0;"
        allowfullscreen=""
        loading="lazy"
        title="Location Map">
</iframe>
```

### 6. Complete Page Structure

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Media Gallery</title>
</head>
<body>
    <header>
        <h1>Media Gallery</h1>
        <nav>
            <ul>
                <li><a href="#images">Images</a></li>
                <li><a href="#audio">Audio</a></li>
                <li><a href="#video">Video</a></li>
                <li><a href="#embeds">Embeds</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <section id="images">
            <h2>Image Gallery</h2>
            <!-- Image gallery here -->
        </section>

        <section id="audio">
            <h2>Audio Tracks</h2>
            <!-- Audio players here -->
        </section>

        <section id="video">
            <h2>Video Gallery</h2>
            <!-- Video players here -->
        </section>

        <section id="embeds">
            <h2>Embedded Content</h2>
            <!-- iframes here -->
        </section>
    </main>

    <footer>
        <p>&copy; 2024 Media Gallery</p>
    </footer>
</body>
</html>
```

## Media Elements Summary

| Element | Purpose | Key Attributes |
|---------|---------|----------------|
| `<img>` | Display image | src, alt, width, height, loading |
| `<picture>` | Responsive images | Contains `<source>` and `<img>` |
| `<source>` | Media source | src, srcset, media, type |
| `<figure>` | Self-contained media | Contains media + figcaption |
| `<figcaption>` | Media caption | Text content |
| `<audio>` | Audio player | controls, preload, autoplay, loop, muted |
| `<video>` | Video player | controls, poster, width, height, preload |
| `<track>` | Text tracks | kind, src, srclang, label, default |
| `<iframe>` | Embed content | src, width, height, title, loading |

## Bonus Challenges

1. **Lightbox**: Create a thumbnail grid that expands on click
2. **Playlist**: Create an audio playlist with track listing
3. **Video Chapters**: Add chapter markers using `<track kind="chapters">`
4. **SVG Image**: Include an inline SVG graphic
5. **Lazy Loading**: Implement native lazy loading for all images

## Skills Tested
- Image elements (`img`, `picture`, `source`, `figure`, `figcaption`)
- Audio element with multiple sources
- Video element with poster and tracks
- Accessibility (alt text, captions, titles)
- Responsive images (srcset, sizes, picture)
- Embedded content (iframe)

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `media-gallery-v1.html`

Build the media gallery using only semantic HTML5 - no styling.

**Focus on:**
- Proper use of figure, figcaption
- Audio and video elements with controls
- Picture element for responsive images
- Accessibility (alt text, captions, track elements)

**Expected output:** A functional but unstyled gallery with working media players.

---

### Version 2: HTML + CSS3
**Files:** `media-gallery-v2.html`, `media-gallery-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- CSS Grid gallery layout
- Image hover effects (zoom, overlay)
- Lightbox effect (using :target or checkbox hack)
- Custom audio/video player styling
- Aspect ratio boxes for media
- Responsive grid with auto-fit

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
.gallery { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem; }
.gallery-item { aspect-ratio: 16/9; overflow: hidden; }
.gallery-item img { transition: transform 0.3s; }
.gallery-item:hover img { transform: scale(1.1); }
.overlay { opacity: 0; transition: opacity 0.3s; }
.gallery-item:hover .overlay { opacity: 1; }
```

---

### Version 3: HTML + Bootstrap 5
**File:** `media-gallery-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Grid system for gallery layout
- Card component for media items
- Ratio classes for aspect ratios (ratio ratio-16x9)
- Modal for lightbox functionality
- Carousel for image slideshow
- Nav tabs for category filtering

**Example Bootstrap gallery:**
```html
<div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col">
        <div class="card h-100">
            <div class="ratio ratio-16x9">
                <img src="image.jpg" class="card-img-top object-fit-cover" alt="...">
            </div>
            <div class="card-body">
                <h5 class="card-title">Image Title</h5>
            </div>
        </div>
    </div>
</div>

<!-- Modal for lightbox -->
<div class="modal fade" id="lightbox">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <img src="" class="img-fluid" id="lightbox-img">
        </div>
    </div>
</div>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `media-gallery-v1.html` | Raw HTML version |
| `media-gallery-v2.html` | CSS3 enhanced version |
| `media-gallery-v2.css` | CSS3 stylesheet |
| `media-gallery-v3.html` | Bootstrap version |

### Folder Structure
```
your-repo/
└── 04-html/
    ├── media-gallery-v1.html
    ├── media-gallery-v2.html
    ├── media-gallery-v2.css
    └── media-gallery-v3.html
```

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| Media elements correct | 15 |
| Accessibility (alt, captions) | 15 |
| **Version 2 (CSS3)** | **35** |
| Grid gallery layout | 15 |
| Hover effects | 10 |
| Responsive design | 10 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap grid/cards | 15 |
| Modal/Carousel used | 10 |
| Ratio classes | 10 |
| **Total** | **100** |

---

## Resources for Sample Media
- Images: https://picsum.photos or https://placeholder.com
- Audio: Use any royalty-free audio or create placeholder references
- Video: Use any royalty-free video or create placeholder references
