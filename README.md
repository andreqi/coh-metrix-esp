# Coh-Metrix-Esp
[**Coh-Metrix-Esp**](https://www.aclweb.org/anthology/L16-1745/) is a Spanish version of [Coh-Metrix](http://cohmetrix.com/), able to calculate **45 readability indices** based on psycholingistics metrics of text cohesion and coherence. 

## Installation

**1. Install Freeling**

For most of its Natural Language Processing needs, Coh-Metrix-Esp uses [Freeling](http://nlp.lsi.upc.edu/freeling/index.php). 

If you are using Linux or Windows, follow the instructions [here](https://freeling-user-manual.readthedocs.io/en/latest/installation/installation-packages/) to install the library. 

If you are using macOS, you can install Freeling using homebrew ([*How to install Homebrew?*](https://brew.sh/)): `brew install freeling`

**2. Build the Freeling Java API**

*If you are using macOS, you can skip this step since we already include the necessary library file.*

This step is required for Coh-Metrix-Esp to be able to use Freeling's functionalities. Please, follow the appropriate instructions for [Windows](https://github.com/TALP-UPC/FreeLing/blob/master/APIs/java/README.Win.md) or [Linux](https://github.com/TALP-UPC/FreeLing/blob/master/APIs/java/README.Unix.md).

**3. Download Coh-Metrix-Esp**

Download the `coh-metrix-esp.jar` file.

## Running Coh-Metrix-Esp

Once Coh-Metrix-Esp has been installed, you can run it in the command-line using:

```
java -jar coh-metrix-esp.jar -i <input_text_file> [-o <output_json_file>]
```

For example:

```
java -jar coh-metrix-esp.jar -i testfiles/smallFile.txt
```

This command will compute the metrics for `testfiles/smallFile.txt` and save them in a file named `output.json` by default.


If you want to specify a name and location for the output file, you can use the `-o, --outputFile` option:

```
java -jar coh-metrix-esp.jar -i testfiles/smallFile.txt -o /path/to/metricsSmallFile.json
```

This command will compute the metrics for `testfiles/smallFile.txt` and save them in `/path/to/metrics_for_small_file.json`.


## Licence

Coh-Metrix-Esp is licenced under [Affero GPL](https://www.gnu.org/licenses/agpl-3.0.html).

## Citation

If you use Coh-Metrix-Esp in your research, please cite [Coh-Metrix-Esp: A Complexity Analysis Tool for Documents Written in Spanish](https://www.aclweb.org/anthology/L16-1745/).


> Andre Quispersaravia, Walter Perez, Marco Sobrevilla and Fernando Alva-Manchego. 2016. 
> Coh-Metrix-Esp: A Complexity Analysis Tool for Documents Written in Spanish. 
> In Proceedings of the Tenth International Conference on Language Resources and Evaluation (LREC’16), pages 4694-4698, Portorož, Slovenia. 
> European Language Resources Association (ELRA).

```
@inproceedings{quispesaravia-etal-2016-coh,
    title = "{C}oh-{M}etrix-{E}sp: {A} Complexity Analysis Tool for Documents Written in {S}panish",
    author = "Quispesaravia, Andre  and
      Perez, Walter  and
      Sobrevilla Cabezudo, Marco  and
      Alva-Manchego, Fernando",
    booktitle = "Proceedings of the Tenth International Conference on Language Resources and Evaluation ({LREC}'16)",
    month = may,
    year = "2016",
    address = "Portoro{\v{z}}, Slovenia",
    publisher = "European Language Resources Association (ELRA)",
    url = "https://www.aclweb.org/anthology/L16-1745",
    pages = "4694--4698",
}
```

## Acknowledgments
We are extremely grateful to Sam Harrison for helping create an installable and runable version of Coh-Metrix-Esp.
